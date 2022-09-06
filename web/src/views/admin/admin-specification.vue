<template>
  <p>
    <a-button type="primary" @click="addSpecification()">新增</a-button>
  </p>
  <a-table :dataSource="specificationList"
           :columns="columns"
           :row-key="record=>record.id"
           :pagination="pagination"
           :loading="loading"
           @change="handleTableChange">
    <template v-slot:action="{text,record}">

      <a-button type="primary" @click="showModal(record)">编辑</a-button>
      <a-popconfirm
          title="确认要删除吗?"
          ok-text="是的"
          cancel-text="点错了"
          @confirm="deleteSpecification(record.id)"
      >
        <a-button type="danger">删除</a-button>
      </a-popconfirm>
    </template>
  </a-table>

  <a-modal v-model:visible="visible" width="1000px" title="Basic Modal" @ok="handleOk(specification)">
    <a-form
        :model="specification"
        name="basic"
        :label-col="{ span: 3 }"
        :wrapper-col="{ span: 20 }"
    >
      <a-form-item label="规格编号">
        <a-input v-model:value="specification.tbSpecification.id " disabled="true"/>
      </a-form-item>
      <a-form-item label="规格名称">
        <a-input v-model:value="specification.tbSpecification.specName "/>
      </a-form-item>
      <a-form-item label="规格项">
        <a-button type="primary" @click="addTr">新增</a-button>
        <!--                <table>-->
        <!--                  <thead>-->
        <!--                  <tr>-->
        <!--                    <td>规格选项</td>-->
        <!--                    <td>排序</td>-->
        <!--                    <td>操作</td>-->
        <!--                  </tr>-->
        <!--                  </thead>-->
        <!--                  <tbody>-->
        <!--                  <tr v-for="(pojo,index) in specification.tbSpecificationOptions" v-bind:key="pojo.id">-->
        <!--                    <td>-->
        <!--                      <a-input v-model:value="pojo.optionName"/>-->
        <!--                    </td>-->
        <!--                    <td>-->
        <!--                      <a-input v-model:value="pojo.orders"/>-->
        <!--                    </td>-->
        <!--                    <td>-->
        <!--                      <a-button type="danger" @click="delTr(index)">删除</a-button>-->
        <!--                    </td>-->
        <!--                  </tr>-->
        <!--                  </tbody>-->
        <!--                </table>-->


        <a-table :dataSource="specification.tbSpecificationOptions"
                 :columns="columnsList"
                 :row-key="record=>record.id"
                 :loading="loading"
                 :scroll="{ y: 240 }"
                 @change="ggg"
        >
          <template v-slot:optionName="{text,record}">
            <a-input v-model:value="record.optionName"/>
          </template>
          <template v-slot:orders="{text,record}">
            <a-input v-model:value="record.orders"/>
          </template>
          <template v-slot:author="{text,record,index,currentPageData}">
            <a-button type="danger" @click="delTr(index,currentPageData)">删除</a-button>
          </template>
        </a-table>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue';
import axios from "axios";
import {message} from "ant-design-vue";

export default defineComponent({
  name: 'AdminSpecification',
  setup() {
    // 模态框开关
    const visible = ref<boolean>(false);
    //规格列表
    const specificationList = ref();
    //规格对象
    const specification = ref<any>({
      tbSpecification: {},
      tbSpecificationOptions: [{
        optionName: '',
        orders: ''
      }]
    });
    //加载动画
    const loading = ref(false);
    //table
    const columns = [
      {
        title: '规格编号',
        dataIndex: 'id',
        key: 'id',
      },
      {
        title: '规格名称',
        dataIndex: 'specName',
        key: 'specName',
      },
      {
        title: '操作',
        key: 'author',
        slots: {customRender: 'action'}
      }
    ];
    //table2
    const columnsList = [
      {
        title: '编号',
        dataIndex: 'id',
        key: 'id',
      },
      {
        title: '名称',
        dataIndex: 'optionName',
        key: 'optionName',
        slots: {customRender: 'optionName'}
      },
      {
        title: '排序值',
        dataIndex: 'orders',
        key: 'orders',
        slots: {customRender: 'orders'}
      },
      {
        title: '操作',
        key: 'author',
        slots: {customRender: 'author'}
      }
    ];
    //分页组件
    const pagination = ref({
      current: 1,
      pageSize: 5,
      total: 0
    });
    //分页组件
    const pageNum = ref({
      num: 0
    });
    // 新增栏
    const addTr = () => {
      specification.value.tbSpecificationOptions.push({});
    }
    //删除栏
    const delTr = (index: any) => {
      index = pageNum.value.num + index;
      specification.value.tbSpecificationOptions.splice(index, 1);
    }
    //修改
    const showModal = (params: any) => {
      axios.post("http://localhost:8899/mall-manager/specification/querySpec", params).then((response) => {
        if (response.data.code === 200) {
          specification.value = response.data.data;
          visible.value = true;
        } else {
          message.error(response.data.msg);
        }
      });
    };
    //新增
    const addSpecification = () => {
      specification.value = {
        tbSpecification: {},
        tbSpecificationOptions: []
      };
      visible.value = true;
    };
    // 模态框-确认
    const handleOk = (params: any) => {
      updateOrInsert(params);
      visible.value = false;
    };
    //查询品牌列表
    const handleQuery = (params: any) => {
      loading.value = true;
      axios.get("http://localhost:8899/mall-manager/specification/queryAllSpec", {
        params: {
          page: params.page,
          size: params.size
        }
      }).then((response) => {
        specificationList.value = response.data.data.list;
        pagination.value.current = params.page;
        pagination.value.total = response.data.data.total;

        loading.value = false;
      });
    };
    //新增或修改请求
    const updateOrInsert = (params: any) => {
      axios.post("http://localhost:8899/mall-manager/specification/updateOrInsert", params).then((response) => {
        if (response.data.code === 200) {
          message.success(response.data.msg);
          handleQuery({
            page: pagination.value.current,
            size: pagination.value.pageSize
          });
        } else {
          message.error(response.data.msg);
        }
      });
    }
    //分页导航触发
    const handleTableChange = (pagination: any) => {
      handleQuery({
        page: pagination.current,
        size: pagination.pageSize
      });
    };

    const deleteSpecification = (id: number) => {
      axios.delete("http://localhost:8899/mall-manager/specification/deleteSpec/" + id).then((response) => {
        if (response.data.code === 200) {
          message.success(response.data.msg);
          handleQuery({
            page: pagination.value.current,
            size: pagination.value.pageSize
          });
        } else {
          message.error(response.data.msg);
        }
      })
    }
    //计算分页后对象处于数组的第几个
    const ggg = (e: any) => {
      pageNum.value.num = (e.current - 1) * e.pageSize;
    };
    onMounted(() => {
      handleQuery({
        page: 1,
        size: pagination.value.pageSize
      });
    });

    return {
      visible,
      specificationList,
      columns,
      pagination,
      loading,
      specification,
      columnsList,
      handleQuery,
      showModal,
      handleOk,
      deleteSpecification,
      handleTableChange,
      addSpecification,
      addTr,
      delTr,
      ggg
      // updateOrInsert,
      // deleteBrand,
    };
  },
});
</script>

<style scoped>

</style>