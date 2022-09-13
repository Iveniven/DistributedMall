<template>
  <p>
    <a-button type="primary" @click="addTemp()">新增</a-button>
  </p>
  <a-table :dataSource="TempList"
           :columns="columns"
           :row-key="record=>record.id"
           :pagination="pagination"
           :loading="loading"
           @change="handleTableChange">


    <template v-slot:specList="{record}">
      <a-collapse>
        <a-collapse-panel header="点击展开">
          <a-list size="small" bordered :data-source="record.specList">
            <template #renderItem="{ item }">
              <a-list-item>{{ item.specName }}</a-list-item>
            </template>
          </a-list>
        </a-collapse-panel>
      </a-collapse>
    </template>


    <template v-slot:brandList="{record}">
      <a-collapse>
        <a-collapse-panel header="点击展开">
          <a-list size="small" bordered :data-source="record.brandList">
            <template #renderItem="{ item }">
              <a-list-item>{{ item.name }}</a-list-item>
            </template>
          </a-list>
        </a-collapse-panel>
      </a-collapse>
    </template>


    <template v-slot:customAttributeList="{record}">
      <a-collapse>
        <a-collapse-panel header="点击展开">
          <a-list size="small" bordered :data-source="record.customAttributeList">
            <template #renderItem="{ item }">
              <a-list-item>{{ item.text }}</a-list-item>
            </template>
          </a-list>
        </a-collapse-panel>
      </a-collapse>
    </template>


    <template v-slot:action="{text,record}">
      <a-space wrap>
      <a-button type="primary" @click="editTemp(record)">编辑</a-button>
      <a-popconfirm
          title="确认要删除吗?"
          ok-text="是的"
          cancel-text="点错了"
          @confirm="deleteTemp(record.id)"
      >
        <a-button type="danger">删除</a-button>
      </a-popconfirm>
      </a-space>
    </template>
  </a-table>

  <a-modal v-model:visible="visible" width="1000px" title="Basic Modal" @cancel="cancelModal"
           @ok="updateOrInsert(Temp)">
    <a-form
        :model="Temp"
        name="basic"
        :label-col="{ span: 3 }"
        :wrapper-col="{ span: 20 }"
    >
      <a-form-item label="模板编号">
        <a-input v-model:value="Temp.id" disabled="true"/>
      </a-form-item>
      <a-form-item label="模板名称">
        <a-input v-model:value="Temp.name"/>
      </a-form-item>
      <a-form-item label="关联规格">
        <a-transfer
            v-model:target-keys="targetKeys1"
            :data-source="mockData1"
            show-search
            :filter-option="filterOption1"
            :render="item => item.title"
            @change="handleChange1"
            @search="handleSearch1"
        />
      </a-form-item>
      <a-form-item label="关联品牌">
        <a-transfer
            v-model:target-keys="targetKeys"
            :data-source="mockData"
            show-search
            :filter-option="filterOption"
            :render="item => item.title"
            @change="handleChange"
            @search="handleSearch"
        />
      </a-form-item>
      <a-form-item label="自定义属性">
        <a-button type="primary" @click="addTr">新增</a-button>
        <a-table :dataSource="Temp.customAttributeList"
                 :columns="columnsList"
                 :row-key="record=>record"
                 :loading="loading"
                 :scroll="{ y: 240 }"
        >
          <template v-slot:customAttributeText="{text,record}">
            <a-input v-model:value="record.text"/>
          </template>
          <template v-slot:action="{text,record,index}">
            <a-button type="danger" @click="delTr(index)">删除</a-button>
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

interface MockData {
  key: string;
  title: string;
  description: string;
  chosen: boolean;
}

export default defineComponent({
  name: 'AdminTemplates',
  setup: function () {

    //table
    const columnsList = [
      {
        title: '属性名称',
        key: 'text',
        slots: {customRender: 'customAttributeText'}
      },
      {
        title: '操作',
        key: 'action',
        slots: {customRender: 'action'}
      }
    ];


    const mockData1 = ref<MockData[]>([]);
    const targetKeys1 = ref<any[]>([]);
    const getMock1 = (params: any, unselect: any) => {
      const keys = [];
      const mData = [];
      for (let i = 0; i < params.length; i++) {
        const data = {
          key: params[i],
          title: params[i].specName,
          description: `description of ` + params[i].specName,
          chosen: true,
        };
        if (data.chosen) {
          keys.push(data.key);
        }
        mData.push(data);
      }
      for (let i = 0; i < unselect.length; i++) {
        const data = {
          key: unselect[i],
          title: unselect[i].specName,
          description: `description of ` + unselect[i].specName,
          chosen: false,
        };
        if (data.chosen) {
          keys.push(data.key);
        }
        mData.push(data);
      }
      mockData1.value = mData;
      targetKeys1.value = keys;
    };
    const filterOption1 = (inputValue: string, option: MockData) => {
      return option.description.indexOf(inputValue) > -1;
    };
    const handleChange1 = (keys: any[], direction: string, moveKeys: any[]) => {
      Temp.value.specList = keys;
      console.log(keys, direction, moveKeys);
    };
    const handleSearch1 = (dir: string, value: string) => {
      console.log('search:', dir, value);
    };


    const mockData = ref<MockData[]>([]);
    const targetKeys = ref<any[]>([]);
    const getMock = (params: any, unselect: any) => {
      const keys = [];
      const mData = [];
      for (let i = 0; i < params.length; i++) {
        const data = {
          key: params[i],
          title: params[i].name,
          description: `description of ` + params[i].name,
          chosen: true,
        };
        if (data.chosen) {
          keys.push(data.key);
        }
        mData.push(data);
      }
      for (let i = 0; i < unselect.length; i++) {
        const data = {
          key: unselect[i],
          title: unselect[i].name,
          description: `description of ` + unselect[i].name,
          chosen: false,
        };
        if (data.chosen) {
          keys.push(data.key);
        }
        mData.push(data);
      }
      mockData.value = mData;
      targetKeys.value = keys;
    };
    const filterOption = (inputValue: string, option: MockData) => {
      return option.description.indexOf(inputValue) > -1;
    };
    const handleChange = (keys: any[], direction: string, moveKeys: any[]) => {
      Temp.value.brandList = keys;
      console.log(keys, direction, moveKeys);
    };
    const handleSearch = (dir: string, value: string) => {
      console.log('search:', dir, value);
    };


    //模板列表
    const TempList = ref();
    //模板对象
    const Temp = ref();
    //加载动画
    const loading = ref(false);
    // 模态框开关
    const visible = ref<boolean>(false);
    //table
    const columns = [
      {
        title: '模板编号',
        dataIndex: 'id',
        key: 'id',
      },
      {
        title: '模板名称',
        dataIndex: 'name',
        key: 'name',
      },
      {
        title: '关联规格',
        key: 'specList',
        slots: {customRender: 'specList'}
      },
      {
        title: '关联品牌',
        key: 'brandList',
        slots: {customRender: 'brandList'}
      },
      {
        title: '自定义属性',
        key: 'customAttributeList',
        slots: {customRender: 'customAttributeList'}
      },
      {
        title: '操作',
        key: 'author',
        slots: {customRender: 'action'}
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
    //分页导航触发
    const handleTableChange = (pagination: any) => {
      handleQuery({
        page: pagination.current,
        size: pagination.pageSize
      });
    };
    //查询品牌列表
    const handleQuery = (params: any) => {
      loading.value = true;
      axios.get("http://localhost:8899/mall-manager/template/queryAllTemp", {
        params: {
          page: params.page,
          size: params.size
        }
      }).then((response) => {
        TempList.value = response.data.data.list;
        for (let i = 0; i < TempList.value.length; i++) {
          TempList.value[i].brandList = JSON.parse(TempList.value[i].brandIds)
          TempList.value[i].specList = JSON.parse(TempList.value[i].specIds)
          TempList.value[i].customAttributeList = JSON.parse(TempList.value[i].customAttributeItems)
        }
        pagination.value.current = params.page;
        pagination.value.total = response.data.data.total;

        loading.value = false;
      }).catch(err => {
        console.log(err)
      });
    };
    //新增
    const addTemp = () => {
      Temp.value = {
        customAttributeList:[]
      };
      axios.get("http://localhost:8899/mall-manager/brand/queryBrandList").then((response) => {
        getMock([], response.data.data);
      }).catch(err => {
        console.log(err)
      });
      axios.get("http://localhost:8899/mall-manager/specification/queryAllSpecList").then((response) => {
        getMock1([], response.data.data);
      }).catch(err => {
        console.log(err)
      });
      visible.value = true;
    };
    //编辑
    const editTemp = (brands: any) => {
      Temp.value = brands
      axios.post("http://localhost:8899/mall-manager/template/queryNotSelectTbBrand", Temp.value.brandList).then((response) => {
        getMock(Temp.value.brandList, response.data.data);
      }).catch(err => {
        console.log(err)
      });
      axios.post("http://localhost:8899/mall-manager/template/queryNotSelectSpecs", Temp.value.specList).then((response) => {
        getMock1(Temp.value.specList, response.data.data);
      }).catch(err => {
        console.log(err)
      });
      visible.value = true;
    }
    //退出清除
    const cancelModal = () => {
      mockData1.value = []
      targetKeys1.value = []
      mockData.value = []
      targetKeys.value = []

      Temp.value = {}
    }
    //提交
    const updateOrInsert = (params: any) => {
      let list1:Array<any> = [];
      (params.specList as Array<any>).forEach(item=>{
        list1.push({
          id:item.id,
          specName:item.specName
        })
      })

      let list2:Array<any> = [];
      (params.brandList as Array<any>).forEach(item=>{
        list2.push({
          id:item.id,
          name:item.name
        })
      })

      params.specIds = JSON.stringify(list1);
      params.brandIds = JSON.stringify(list2);
      params.customAttributeItems = JSON.stringify(params.customAttributeList);
      console.log(params)
      axios.post("http://localhost:8899/mall-manager/template/updateOrInsert", params).then((response) => {
        if (response.data.code === 200) {
          message.success(response.data.msg);
          handleQuery({
            page: pagination.value.current,
            size: pagination.value.pageSize
          });
        } else {
          message.error(response.data.msg);
        }
      }).catch(err => {
        console.log(err)
      });
      visible.value = false;
    }


    //新增栏
    const addTr = () => {
      Temp.value.customAttributeList.push({});
    }
    //删除栏
    const delTr = (index: number) => {
      index = pageNum.value.num + index;
      Temp.value.customAttributeList.splice(index, 1);
    }
    //计算分页后对象处于数组的第几个
    const ggg = (e: any) => {
      pageNum.value.num = (e.current - 1) * e.pageSize;
    };


    const deleteTemp = (id:number) =>{
      axios.delete("http://localhost:8899/mall-manager/template/deleteTemp/"+id).then((response) => {
        if (response.data.code === 200) {
          message.success(response.data.msg);
          handleQuery({
            page: pagination.value.current,
            size: pagination.value.pageSize
          });
        } else {
          message.error(response.data.msg);
        }
      }).catch(err => {
        console.log(err)
      });
    }


    onMounted(() => {
      handleQuery({
        page: 1,
        size: pagination.value.pageSize
      });
    });

    return {

      mockData,
      targetKeys,
      filterOption,
      handleChange,
      handleSearch,

      mockData1,
      targetKeys1,
      filterOption1,
      handleChange1,
      handleSearch1,

      Temp,
      TempList,
      columns,
      columnsList,
      pagination,
      loading,
      visible,
      addTr,
      delTr,
      addTemp,
      handleQuery,
      handleTableChange,
      cancelModal,
      editTemp,
      updateOrInsert,
      ggg,
      deleteTemp,
    };
  }
});
</script>

<style scoped>
:deep(.ant-transfer-list ){
  width: 50%
}
</style>