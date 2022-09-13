<template>
  <p>
    <a-button type="primary" @click="addBrand()">新增</a-button>
  </p>
  <a-table :dataSource="brandList"
           :columns="columns"
           :row-key="record=>record.id"
           :pagination="pagination"
           :loading="loading"
           @change="handleTableChange">
    <template v-slot:action="{text,record}">
      <a-space wrap>
      <a-button type="primary" @click="showModal(record)">编辑</a-button>
      <a-popconfirm
          title="确认要删除吗?"
          ok-text="是的"
          cancel-text="点错了"
          @confirm="deleteBrand(record.id)"
      >
        <a-button type="danger">删除</a-button>
      </a-popconfirm>
      </a-space>
    </template>
  </a-table>
  <a-modal v-model:visible="visible" title="Basic Modal" @ok="handleOk(brand)">
    <a-form
        :model="brand"
        name="basic"
        :label-col="{ span: 4 }"
        :wrapper-col="{ span: 19 }"
    >
      <a-form-item label="品牌编号">
        <a-input v-model:value="brand.id" disabled="true"/>
      </a-form-item>
      <a-form-item label="品牌名称">
        <a-input v-model:value="brand.name"/>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue';
import axios from "axios";
import {message} from "ant-design-vue";

export default defineComponent({
  name: 'AdminBrand',
  setup() {
    // 模态框开关
    const visible = ref<boolean>(false);
    //品牌列表
    const brandList = ref();
    //品牌对象
    const brand = ref({});
    //加载动画
    const loading = ref(false);
    //table
    const columns = [
      {
        title: '品牌编号',
        dataIndex: 'id',
        key: 'id',
      },
      {
        title: '品牌名称',
        dataIndex: 'name',
        key: 'name',
      },
      {
        title: '品牌首字母',
        dataIndex: 'firstChar',
        key: 'firstChar',
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
    //修改
    const showModal = (params: any) => {
      brand.value = params;
      visible.value = true;
    };
    //新增
    const addBrand = () => {
      brand.value = {};
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
      axios.get("http://localhost:8899/mall-manager/brand/queryBrands", {
        params: {
          page: params.page,
          size: params.size
        }
      }).then((response) => {
        brandList.value = response.data.data.list;
        pagination.value.current = params.page;
        pagination.value.total = response.data.data.total;

        loading.value = false;
      });
    };
    //新增或修改请求
    const updateOrInsert = (params: any) => {
      axios.post("http://localhost:8899/mall-manager/brand/updateOrInsert", params).then((response) => {
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

    const deleteBrand = (id:number) => {
      axios.delete("http://localhost:8899/mall-manager/brand/deleteBrand/" + id).then((response) => {
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
    onMounted(() => {
      handleQuery({
        page: 1,
        size: pagination.value.pageSize
      });
    });
    return {
      visible,
      brandList,
      columns,
      pagination,
      loading,
      brand,
      handleQuery,
      showModal,
      handleOk,
      handleTableChange,
      addBrand,
      updateOrInsert,
      deleteBrand,
    };
  },
})
;
</script>

<style scoped>

</style>