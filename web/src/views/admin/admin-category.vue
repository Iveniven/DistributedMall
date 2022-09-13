<template>
  <a-breadcrumb>
    <a-breadcrumb-item v-for="(items,index) in list" v-bind:key="items.id">
      <a href="#" @click="handleQuery(items,index)">{{items.name }}</a>
    </a-breadcrumb-item>
  </a-breadcrumb>
  <br>
  <a-table :dataSource="itemCatList"
           :columns="columns"
           :row-key="record=>record.id"
           :loading="loading"
           :scroll="{ y: 750 }"
  >
    <template v-slot:typeId="{record}">
      {{ record.tbTypeTemplate.name }}
    </template>

    <template v-slot:action="{record}">
      <a-space wrap>
        <a-button type="primary" @click="handleQuery(record,list.length)" v-if="list.length<3">查询下一级</a-button>
        <a-button type="primary" @click="showModal(record)">编辑</a-button>
      </a-space>
    </template>

  </a-table>


  <a-modal v-model:visible="visible" title="编辑" @ok="handleOk">
    <a-form
        :model="itemCat"
        name="basic"
        :label-col="{ span: 4 }"
        :wrapper-col="{ span: 19 }"
    >
      <a-form-item label="分类编号">
        <a-input v-model:value="itemCat.id" disabled="true"/>
      </a-form-item>
      <a-form-item label="上级分类">
        <a-select
            v-model:value="itemCat.parentId"
            show-search
            placeholder="Select a person"
            style="width: 200px"
            :options="options1"
            :filter-option="filterOption1"
            :disabled="list.length>1?false:true"
            @change="handleChange1"
        ></a-select>
      </a-form-item>
      <a-form-item label="分类名称">
        <a-input v-model:value="itemCat.name"/>
      </a-form-item>
      <a-form-item label="类型模板">
        <a-select
            v-model:value="itemCat.typeId"
            show-search
            placeholder="Select a person"
            style="width: 200px"
            :options="options"
            :filter-option="filterOption"
            @change="handleChange"
        ></a-select>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue';
import type { SelectProps } from 'ant-design-vue';
import axios from "axios";
import {message} from "ant-design-vue";

export default defineComponent({
  name: 'AdminCategory',
  setup: function () {
    //itemCatList
    const itemCatList = ref();
    //ParentItemCatList
    const ParentItemCatList = ref();
    //ItemCat
    const itemCat = ref();
    //模板列表
    const TempList =ref();
    //加载动画
    const loading = ref(false);
    //面包屑
    const list = ref([{id: 0, name: '顶级分类列表',parentId:0}])
    //table
    const columns = [
      {
        title: '分类编号',
        dataIndex: 'id',
        key: 'id',
      },
      {
        title: '分类名称',
        dataIndex: 'name',
        key: 'name',
      },
      {
        title: '类型模板名称',
        dataIndex: 'typeId',
        key: 'typeId',
        slots: {customRender: 'typeId'}

      },
      {
        title: '操作',
        key: 'action',
        slots: {customRender: 'action'}
      }
    ];
    //查询品牌列表
    const handleQuery = (params: any, num: number) => {
      loading.value = true;
      if (params.id !== 0) {
        list.value.push(params)
      }
      for (let i = list.value.length - 1; i > 0; i--) {
        if (i > num) {
          list.value.pop();
        }
      }
      axios.get("http://localhost:8899/mall-manager/itemCat/queryItemsByParentId/" + params.id).then((response) => {
        itemCatList.value = response.data.data;
        loading.value = false;
      });
    };


    const visible = ref<boolean>(false);

    const showModal = (params: any) => {
      itemCat.value = params;

      //查询品牌列表

        axios.get("http://localhost:8899/mall-manager/template/queryAllTempList").then((response) => {
          loading.value = true;
          TempList.value = response.data.data;
          options.value = [...Array(TempList.value.length)].map((_, i) => ({
            value: TempList.value[i].id,
            label: TempList.value[i].name
          }));
          loading.value = false;
        }).catch(err => {
          console.log(err)
        });

      axios.get("http://localhost:8899/mall-manager/itemCat/queryItemsByParentId/" + list.value[list.value.length-1].parentId).then((response) => {
        loading.value = true;
        ParentItemCatList.value = response.data.data;
        options1.value = [...Array(ParentItemCatList.value.length)].map((_, i) => ({
          value: ParentItemCatList.value[i].id,
          label: ParentItemCatList.value[i].name
        }));
        loading.value = false;
      });


      visible.value = true;
    };

    const handleOk = () => {
      axios.post("http://localhost:8899/mall-manager/itemCat/updateOrInsert" , itemCat.value).then((response) => {
        if (response.data.code === 200) {
          message.success(response.data.msg);
          handleQuery({id:list.value[list.value.length-1].id}, list.value.length - 1);
        } else {
          message.error(response.data.msg);
        }
      });
      visible.value = false;
    };



    const options = ref<SelectProps['options']>([]);

    const handleChange = (value: number) => {
      itemCat.value.typeId = value
      console.log(`selected ${value}`);
    };
    const filterOption = (input: string, option: any) => {
      return option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0;
    };




    const options1 = ref<SelectProps['options']>([]);

    const handleChange1 = (value: number) => {
      itemCat.value.parentId = value
      console.log(`selected ${value}`);
    };
    const filterOption1 = (input: string, option1: any) => {
      return option1.label.toLowerCase().indexOf(input.toLowerCase()) >= 0;
    };

    onMounted(() => {
      handleQuery({id: 0}, 0);
    });

    return {
      value: ref<string | undefined>(undefined),
      loading,
      columns,
      itemCatList,
      list,
      visible,
      itemCat,
      options,
      options1,
      TempList,
      ParentItemCatList,
      showModal,
      handleOk,
      handleQuery,
      filterOption,
      filterOption1,
      handleChange,
      handleChange1,
    }
  }
});
</script>

<style scoped>

</style>