<template>
  <div style="margin-left: 30px">
    <el-row :gutter="20" style="height: 70px">
      <el-col :span="24" style="border-radius: 4px;">
        <el-card shadow="always" style="width: content-box;height: content-box;text-align: left"
                 body-style="padding: 10px 5px 0px 20px;height:60px">
          <el-form :inline="true" :model="formInline" style="margin-top: 5px;height: 40px" :rules="rules"
                   ref="formInline">
            <el-form-item label="级别" prop="id">
              <el-cascader
                style="width: 300px"
                placeholder="请选择学院或专业或班级"
                :props="props"
                :model="formInline.id"
                collapse-tags
                ref="id"
                clearable></el-cascader>
            </el-form-item>
            <el-form-item prop="choice">
              <el-switch
                v-model="formInline.choice"
                active-text="按月"
                inactive-text="按日">
              </el-switch>
            </el-form-item>
            <el-form-item label="月份" v-if="formInline.choice" prop="date">
              <el-date-picker
                type="monthrange"
                unlink-panels
                style="width: 300px"
                v-model="formInline.date"
                range-separator="至"
                start-placeholder="开始月份"
                end-placeholder="结束月份"
                :picker-options="MonthPickerOptions">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="日期" v-if="formInline.choice===false" prop="date">
              <el-date-picker
                type="daterange"
                unlink-panels
                style="width: 300px"
                v-model="formInline.date"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                :picker-options="DayPickerOptions">
              </el-date-picker>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onSubmit('formInline')">查询</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" style="margin-top: 10px;height: 450px">
      <el-col :span="12" style="height: 400px">
        <el-card style="height:inherit;width: content-box;padding: 20px"
                 body-style="height:100%;width: 100%;padding:0px">
          <div id="chart" ref="chart" style="height:100%;width: 100%"></div>
        </el-card>
      </el-col>
      <el-col :span="12" style="height: 400px">
        <el-card style="height:inherit;width: content-box;padding: 20px"
                 body-style="height:100%;width: 100%;padding:0px">
          <div id="chart1" ref="chart1" style="height: 100%;width: 100%"></div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" style="margin-top: 10px;height: 350px">
      <el-col :span="13" style="height: 300px;">
        <el-card style="height:inherit;width: content-box;padding: 20px"
                 body-style="height:100%;width: 100%;padding:0px">
          <div id="chart2" ref="chart2" style="height: 100%;width: 100%;margin-left: 10px"></div>
        </el-card>
      </el-col>
      <el-col :span="10" style="height: 300px">
        <el-card style="height:inherit;width: content-box;padding: 20px"
                 body-style="height:100%;width: 100%;padding:0px">
          <div id="chart3" ref="chart3" style="height:100%;width: 100%"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import axios from 'axios'

const echarts = require('echarts/lib/echarts');
require('echarts/lib/component/title');
require('echarts/lib/component/toolbox');
require('echarts/lib/component/tooltip');
require('echarts/lib/component/grid');
require('echarts/lib/component/legend');
require('echarts/lib/chart/line');
require('echarts/lib/component/markLine')
require('echarts/lib/component/markPoint')
require('echarts/lib/component/dataZoom');
require('echarts/lib/chart/bar');
require('echarts/lib/chart/pie');
export default {
  name: "OneDay",
  data() {
    return {
      rules: {
        // id: [{required: true, message: '请选择级别', trigger: 'change'}],
        date: [{
          type: 'array', required: true, message: '请选择日期', trigger: 'change'
        }]
      },
      formInline: {
        id: '',
        choice: true,
        date: Array,
      },
      props: {
        multiple: true,
        checkStrictly: true,
        lazy: true,
        lazyLoad(node, resolve) {
          const {level} = node;
          let nodes;
          setTimeout(() => {
            axios.post("http://localhost:9090/schoolUser/getData", {
              level: level,
              value: level !== 0 ? node.data.value : null,
            }).then(function (response) {
              let data = response.data;
              if (data.code === 1) {
                nodes = data.data;
                resolve(nodes);
              } else {
                console.log();
              }
            }).catch(function (error) {
              console.log(error);
            });
          }, 1000);
        },
      },
      MonthPickerOptions: {
        shortcuts: [{
          text: '本月',
          onClick(picker) {
            picker.$emit('pick', [new Date(), new Date()]);
          }
        }, {
          text: '今年至今',
          onClick(picker) {
            const end = new Date();
            const start = new Date(new Date().getFullYear(), 0);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
      DayPickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
      monthData: [],
      myChart: '',
      myChart1: '',
      myChart3: '',
      myChart2: '',
      // myChart1: ''
    };
  },
  mounted() {
    this.initChart();
    this.initChart1();
    this.initChart3();
    this.initChart2();
  },
  methods: {
    onSubmit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let _this = this;
          const nodes = _this.$refs['id'].getCheckedNodes();
          let obj = [];
          nodes.forEach(node => {
            obj.push({
              value: node.value,
              label: node.label,
              level: node.level,
            })
          });
          axios.post('http://localhost:9090/schoolUser/getConsume', {
            id: obj,
            choice: _this.formInline.choice,
            date: _this.formInline.date
          }).then(function (response) {
            _this.monthData = response.data.data;
          }).catch(function (error) {
            console.log('error!!');
            console.log(error)
          })
          this.fetchData();
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    initChart() {
      this.myChart = echarts.init(document.getElementById('chart'));
      let option = {
        title: {
          text: '消费平均次统计图'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: []
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: []
        },
        yAxis: {
          type: 'value'
        },
        series: []
      };
      this.myChart.setOption(option);
      this.myChart.hideLoading()
    },
    initChart1() {
      this.myChart1 = echarts.init(document.getElementById('chart1'));
      let option = {
        title: {
          text: '人均消费金额',
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: []
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: []
        },
        yAxis: {
          type: 'value'
        },
        series: []
      };
      this.myChart1.setOption(option);
    },
    initChart3() {
      this.myChart3 = echarts.init(document.getElementById('chart3'));
      let option = {
        title: {
          text: '消费总和',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left',
        },
        series: [
          {
            name: '消费总和',
            type: 'pie',
            radius: '50%',
            data: [],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
      this.myChart3.setOption(option);
      this.myChart3.hideLoading()
    },
    initChart2() {
      this.myChart2 = echarts.init(document.getElementById('chart2'));
      let option = {
        title: {
          text: '人均消费次数',
          left: 'center'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {            // 坐标轴指示器，坐标轴触发有效
            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
          }
        },
        legend: {
          data: []
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            data: []
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: []
      };
      this.myChart2.setOption(option);
      this.myChart2.hideLoading()
    },
    fetchData() {
      this.tableData = [];
      this.myChart.showLoading();
      this.myChart1.showLoading();
      this.myChart3.showLoading();
      this.myChart2.showLoading();
      let name = [];
      this.monthData.forEach(item => (
        name.push(item.name)
      ));
      let date = new Set();
      if (this.formInline.choice) {
        this.monthData.forEach(item => (
          item.consumeMonthData.forEach(item1 => (
            date.add(item1.year + "-" + item1.month)
          ))
        ));
      } else {
        this.monthData.forEach(item => (
          item.consumeDayData.forEach(item1 => (
            date.add(item1.date + "")
          ))
        ));
      }
      date = Array.from(date).sort();
      let dataResult = [];
      let dataResult1 = [];
      let dataResult2 = [];
      let dataResult3 = Array.from({length: name.length}).map(item => ({
        "value": 0,
        "name": ''
      }))
      for (let i = 0; i < this.monthData.length; i++) {
        let temp = {
          name: this.monthData[i].name,
          type: 'line',
          // stack: '平均人次消费',
          data: Array.from({length: date.length}).map(item => (0)),
        };
        let temp1 = {
          name: this.monthData[i].name,
          type: 'line',
          data: Array.from({length: date.length}).map(item => (0)),
        };
        let temp2_1 = {
          name: '',
          type: 'bar',
          stack: '',
          emphasis: {
            focus: 'series'
          },
          data: []
        };
        let temp2_2 = {
          name: '',
          type: 'bar',
          stack: '',
          emphasis: {
            focus: 'series'
          },
          data: []
        }
        if (this.formInline.choice) {
          dataResult3[i].name = this.monthData[i].name;
          temp2_1.name = this.monthData[i].name + '低于人均次数';
          temp2_2.name = this.monthData[i].name +'高于人均次数';
          temp2_1.stack = this.monthData[i].name;
          temp2_2.stack = this.monthData[i].name;
          for (let j = 0; j < this.monthData[i].consumeMonthData.length; j++) {
            let tempDate = this.monthData[i].consumeMonthData[j].year + '-' + this.monthData[i].consumeMonthData[j].month
            for (let k = 0; k < date.length; k++) {
              if (tempDate === date[k]) {
                temp.data[k] = this.monthData[i].consumeMonthData[j].consumption_average_money;
                temp1.data[k] = this.monthData[i].consumeMonthData[j].consumption_student_average_money;
                break;
              }
            }
            dataResult3[i].value += this.monthData[i].consumeMonthData[j].consumption_total_money;
            temp2_1.data.push(this.monthData[i].consumeMonthData[j].student_low_count);
            temp2_2.data.push(this.monthData[i].consumeMonthData[j].student_high_count);
          }
        } else {
          dataResult3[i].name = this.monthData[i].name;
          temp2_1.name = this.monthData[i].name + '低于人均次数';
          temp2_2.name = this.monthData[i].name +'高于人均次数';
          temp2_1.stack = this.monthData[i].name;
          temp2_2.stack = this.monthData[i].name;
          for (let j = 0; j < this.monthData[i].consumeDayData.length; j++) {
            temp2_1.data.push(this.monthData[i].consumeDayData[j].student_low_count);
            temp2_2.data.push(this.monthData[i].consumeDayData[j].student_high_count);
            let tempDate = this.monthData[i].consumeDayData[j].date + ''
            for (let k = 0; k < date.length; k++) {
              if (tempDate === date[k]) {
                temp.data[k] = this.monthData[i].consumeDayData[j].consumption_average_money;
                temp1.data[k] = this.monthData[i].consumeDayData[j].consumption_student_average_money;
                break;
              }
            }
            dataResult3[i].value += this.monthData[i].consumeDayData[j].consumption_total_money;
          }
        }
        dataResult.push(temp);
        dataResult1.push(temp1);
        dataResult2.push(temp2_1);
        dataResult2.push(temp2_2);
      }
      this.myChart.setOption({
        legend: {
          data: name,
        },
        xAxis: {
          data: date,
        },
        series: dataResult,
      });
      this.myChart1.setOption({
        legend: {
          data: name,
        },
        xAxis: {
          data: date,
        },
        series: dataResult1,
      })
      this.myChart3.setOption({
        series: [
          {
            data: dataResult3,
          }
        ]
      });
      this.myChart2.setOption({
        legend: {
          data: name
        },
        xAxis: [
          {
            data: date
          }
        ],
        series: dataResult2
      })
      this.myChart.hideLoading();
      this.myChart1.hideLoading();
      this.myChart3.hideLoading();
      this.myChart2.hideLoading();
    },
  }
}
</script>

<style>
</style>
