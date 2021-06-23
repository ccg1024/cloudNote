<%--
  Created by IntelliJ IDEA.
  User: CCG
  Date: 2021/6/23
  Time: 9:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="ml-3 border rounded content-box" style="background-color: #fff"> <!-- 可重复模块 -->
    <div class="m-4 border-bottom pb-2">
        <img src="./statics/img/icons/square.svg" alt="" >
        <p class="d-inline"><strong>云记报表</strong></p>
    </div>


    <div class="m-4"> <!-- 报表内容 -->
        <div class="row">
            <div class="col">
                <!-- 柱状图的容器 -->
                <div id="monthChart" style="height: 500px"></div>
            </div>
        </div>

    </div>

</div>

<script type="text/javascript" src="statics/js/echarts.min.js"></script>
<script type="text/javascript">

    $.ajax({
       type: "post",
       url: "report",
       data: {
           actionName: "month"
       },
        success: function (result) {
           if (result.code == 1) {
               // 得到月份
               var monthArray = result.result.monthArray;
               // 得到云记数量
               var dataArray = result.result.dataArray;
               loadMonthChart(monthArray, dataArray);
           }
        }
    });
    // 加载柱状图
    function loadMonthChart(monthArray, dataArray) {
        // 基于准备好的dom，初始化echarts实例
        let myChart = echarts.init(document.getElementById('monthChart'));

        // 指定图表的配置项和数据
        var dataAxis = monthArray;
        var data = dataArray;
        var yMax = 30;
        var dataShadow = [];

        for (var i = 0; i < data.length; i++) {
            dataShadow.push(yMax);
        }

        var option = {
            title: {  // 标题
                text: '按月统计',
                subtext: '通过月份查询对应的云记数量',
                left: 'center'  // 标题位置
            },
            tooltip: {},  // 提示框
            xAxis: {
                data: dataAxis,
                axisTick: {
                    show: false
                },
                axisLine: {
                    show: false
                }
            },
            yAxis: {
                axisLine: {
                    show: false
                },
                axisTick: {
                    show: false
                },
                axisLabel: {
                    textStyle: {
                        color: '#999'
                    }
                }
            },
            dataZoom: [
                {
                    type: 'inside'
                }
            ],
            // 系列
            series: [
                {
                    type: 'bar',
                    showBackground: true,
                    itemStyle: {
                        color: new echarts.graphic.LinearGradient(
                            0, 0, 0, 1,
                            [
                                {offset: 0, color: '#83bff6'},
                                {offset: 0.5, color: '#188df0'},
                                {offset: 1, color: '#188df0'}
                            ]
                        )
                    },
                    emphasis: {
                        itemStyle: {
                            color: new echarts.graphic.LinearGradient(
                                0, 0, 0, 1,
                                [
                                    {offset: 0, color: '#2378f7'},
                                    {offset: 0.7, color: '#2378f7'},
                                    {offset: 1, color: '#83bff6'}
                                ]
                            )
                        }
                    },
                    data: data
                }
            ]
        };


        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    }
</script>