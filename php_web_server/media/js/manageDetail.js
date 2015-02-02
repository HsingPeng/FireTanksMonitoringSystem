var Detail = (function () {
        return {
            //main function to initiate the module
            init: function () {},

            initCharts: function () {

                /*var water_gage_data = [
                [gd(09,06,21,12),1],
                [gd(09,10,21,32),3],
                [gd(10,15,13,24),2],
                [gd(10,16,13,24),2],
                [gd(10,18,13,24),1],
                [gd(10,19,13,24),1],
                [gd(10,22,13,24),2],
                ];
            
                function gd(d,h, m, s) {
                return new Date(2015, 01, d,h,m,s).getTime();
                }
            
                showChart_1(water_gage_data);*/

                Detail.get_old_gage();
                Detail.get_old_temp();
                Detail.get_old_level();
            },

            get_old_gage: function () {

                if (rowCount_1 === -1 || row_now_1_old < rowCount_1) {
                    getPageSize_1();
                    getData_1(row_now_1_old);
                } else {
                    /*jslint devel: true */
                    alert("数据已到头!");
                }
            },
            get_new_gage: function () {

                if (row_now_1_new > 0) {
                    getPageSize_1();
                    row_now_1_new_temp = row_now_1_new - page_size_1;
                    getData_1(row_now_1_new_temp < 0 ? 0 : row_now_1_new_temp);
                } else {
                    alert("数据已到头!");
                }
            },
            get_old_temp: function () {

                if (rowCount_2 === -1 || row_now_2_old < rowCount_2) {
                    getPageSize_2();
                    getData_2(row_now_2_old);
                } else {
                    alert("数据已到头!");
                }
            },
            get_new_temp: function () {

                if (row_now_2_new > 0) {
                    getPageSize_2();
                    row_now_2_new_temp = row_now_2_new - page_size_2;
                    getData_2(row_now_2_new_temp < 0 ? 0 : row_now_2_new_temp);
                } else {
                    alert("数据已到头!");
                }
            },
            get_old_level: function () {

                if (rowCount_3 === -1 || row_now_3_old < rowCount_3) {
                    getPageSize_3();
                    getData_3(row_now_3_old);
                } else {
                    alert("数据已到头!");
                }
            },
            get_new_level: function () {

                if (row_now_3_new > 0) {
                    getPageSize_3();
                    row_now_3_new_temp = row_now_3_new - page_size_3;
                    getData_3(row_now_3_new_temp < 0 ? 0 : row_now_3_new_temp);
                } else {
                    alert("数据已到头!");
                }
            }
        };
    }
    ());

var eid = $("#eid").text();
var row_now_1_old = 0;
var row_now_1_new = 0;
var rowCount_1 = -1;
var page_size_1 = 10;

var row_now_2_old = 0;
var row_now_2_new = 0;
var rowCount_2 = -1;
var page_size_2 = 10;

var row_now_3_old = 0;
var row_now_3_new = 0;
var rowCount_3 = -1;
var page_size_3 = 10;

function getURL(type_0, page_size_0, row_now_0) {
    return "getDetails.php?eid=" + eid + "&type=" + type_0 + "&pageSize=" + page_size_0 + "&rowNow=" + row_now_0;
}

function getData_1(row_now_1) {

    function onDataReceived(series) {

        if (series.data === null) {
            alert("获取数据错误: " + series.error);
        } else {
            rowCount_1 = series.rowCount;
            row_now_1_new = series.rowNow;
            row_now_1_old = series.currentCount + series.rowNow;

            var date_1 = series.data, i;

            for (i = 0; i < date_1.length; i++) {
                date_1[i][0] = gDate(date_1[i][0]);
            }

            showChart_1(series.data);
        }
    }

    function onError(XMLHttpRequest, textStatus, errorThrown) {
        alert("获取数据错误: " + textStatus);
    }

    var URL_1 = getURL(1, page_size_1, row_now_1);

    $.ajax({
        url: URL_1,
        type: "GET",
        dataType: "json",
        success: onDataReceived,
        error: onError
    });
}

function getData_2(row_now_2) {

    function onDataReceived(series) {

        if (series.data === null) {
            alert("获取数据错误: " + series.error);
        } else {
            rowCount_2 = series.rowCount;
            row_now_2_new = series.rowNow;
            row_now_2_old = series.currentCount + series.rowNow;

            var date_2 = series.data;

            for (var i = 0; i < date_2.length; i++) {
                date_2[i][0] = gDate(date_2[i][0]);
            }

            showChart_2(series.data);
        }
    }

    function onError(XMLHttpRequest, textStatus, errorThrown) {
        alert("获取数据错误: " + textStatus);
    }

    var URL_2 = getURL(2, page_size_2, row_now_2);

    $.ajax({
        url: URL_2,
        type: "GET",
        dataType: "json",
        success: onDataReceived,
        error: onError
    });
}

function getData_3(row_now_3) {

    function onDataReceived(series) {

        if (series.data == null) {
            alert("获取数据错误: " + series.error);
        } else {
            rowCount_3 = series.rowCount;
            row_now_3_new = series.rowNow;
            row_now_3_old = series.currentCount + series.rowNow;

            var date_3 = series.data;

            for (var i = 0; i < date_3.length; i++) {
                date_3[i][0] = gDate(date_3[i][0]);
            }

            showChart_3(series.data);
        }
    }

    function onError(XMLHttpRequest, textStatus, errorThrown) {
        alert("获取数据错误: " + textStatus);
    }

    var URL_3 = getURL(3, page_size_3, row_now_3);

    $.ajax({
        url: URL_3,
        type: "GET",
        dataType: "json",
        success: onDataReceived,
        error: onError
    });
}

//显示表1
function showChart_1(data_array_1) {

    if (!jQuery.plot) {
        return;
    }

    var options = {
        series: {
            lines: {
                show: true,
                lineWidth: 2,
                fill: true
            },
            points: {
                show: true
            },
            shadowSize: 2
        },
        grid: {
            hoverable: true,
            clickable: true,
            tickColor: "#eee",
            borderWidth: 0
        },
        colors: ["#35aa47"],
        xaxis: {
            mode: "time",
            tickSize: [6, "hour"],
            tickFormatter: function(v, axis) {
                var t = new Date(Math.floor(v));
                return t.toLocaleString();
            }
        },
        yaxis: {
            ticks: 3,
            tickDecimals: 0,
            ticks: [
                [1, "较低"],
                [2, "正常"],
                [3, "充足"]
            ],
            min: 1,
            max: 3
        }
    };

    var plot = $.plot($("#chart_1"), [{
        data: data_array_1,
        label: "水压"
    }], options);

    function showTooltip(x, y, contents) {
        $('<div id="tooltip">' + contents + '</div>').css({
            position: 'absolute',
            display: 'none',
            top: y + 5,
            left: x + 15,
            border: '1px solid #333',
            padding: '4px',
            color: '#fff',
            'border-radius': '3px',
            'background-color': '#333',
            opacity: 0.80
        }).appendTo("body").fadeIn(200);
    }

    var previousPoint = null;
    $("#chart_1").bind("plothover", function(event, pos, item) {
        $("#x").text(pos.x.toFixed(2));
        $("#y").text(pos.y.toFixed(2));

        if (item) {
            if (previousPoint != item.dataIndex) {
                previousPoint = item.dataIndex;

                $("#tooltip").remove();
                var x = item.datapoint[0].toFixed(2),
                    y = item.datapoint[1].toFixed(2);

                var t = new Date(Math.floor(x));
                var result = "正常";
                if (y == 1) {
                    result = "较低";
                } else if (y == 3) {
                    result = "充足";
                }
                showTooltip(item.pageX, item.pageY, t.toLocaleString() + " " + item.series.label + result);
            }
        } else {
            $("#tooltip").remove();
            previousPoint = null;
        }
    });

}

//显示表2
function showChart_2(data_array_2) {

    if (!jQuery.plot) {
        return;
    }

    var options = {
        series: {
            lines: {
                show: true,
                lineWidth: 2,
                fill: false
            },
            points: {
                show: true
            },
            shadowSize: 2
        },
        grid: {
            hoverable: true,
            clickable: true,
            tickColor: "#eee",
            borderWidth: 0
        },
        colors: ["#ffb848"],
        xaxis: {
            mode: "time",
            tickSize: [6, "hour"],
            tickFormatter: function(v, axis) {
                var t = new Date(Math.floor(v));
                return t.toLocaleString();
            }
        },
        yaxis: {
            tickDecimals: 0,
        }
    };

    var plot = $.plot($("#chart_2"), [{
        data: data_array_2,
        label: "水温"
    }], options);

    function showTooltip(x, y, contents) {
        $('<div id="tooltip">' + contents + '</div>').css({
            position: 'absolute',
            display: 'none',
            top: y + 5,
            left: x + 15,
            border: '1px solid #333',
            padding: '4px',
            color: '#fff',
            'border-radius': '3px',
            'background-color': '#333',
            opacity: 0.80
        }).appendTo("body").fadeIn(200);
    }

    var previousPoint = null;
    $("#chart_2").bind("plothover", function(event, pos, item) {
        $("#x").text(pos.x.toFixed(2));
        $("#y").text(pos.y.toFixed(2));

        if (item) {
            if (previousPoint != item.dataIndex) {
                previousPoint = item.dataIndex;

                $("#tooltip").remove();
                var x = item.datapoint[0].toFixed(2),
                    y = item.datapoint[1].toFixed(2);

                var t = new Date(Math.floor(x));

                showTooltip(item.pageX, item.pageY, t.toLocaleString() + " " + item.series.label + Math.floor(y) + "℃");
            }
        } else {
            $("#tooltip").remove();
            previousPoint = null;
        }
    });

}

//显示表3
function showChart_3(data_array_3) {

    if (!jQuery.plot) {
        return;
    }

    var options = {
        series: {
            lines: {
                show: true,
                lineWidth: 2,
                fill: true
            },
            points: {
                show: true
            },
            shadowSize: 2
        },
        grid: {
            hoverable: true,
            clickable: true,
            tickColor: "#eee",
            borderWidth: 0
        },
        colors: ["#37b7f3"],
        xaxis: {
            mode: "time",
            tickSize: [6, "hour"],
            tickFormatter: function(v, axis) {
                var t = new Date(Math.floor(v));
                return t.toLocaleString();
            }
        },
        yaxis: {
            ticks: 4,
            tickDecimals: 0,
            ticks: [
                [1, "过低"],
                [2, "较低"],
                [3, "正常"],
                [4, "充足"]
            ],
            min: 1,
            max: 4
        }
    };

    var plot = $.plot($("#chart_3"), [{
        data: data_array_3,
        label: "水位"
    }], options);

    function showTooltip(x, y, contents) {
        $('<div id="tooltip">' + contents + '</div>').css({
            position: 'absolute',
            display: 'none',
            top: y + 5,
            left: x + 15,
            border: '1px solid #333',
            padding: '4px',
            color: '#fff',
            'border-radius': '3px',
            'background-color': '#333',
            opacity: 0.80
        }).appendTo("body").fadeIn(200);
    }

    var previousPoint = null;
    $("#chart_3").bind("plothover", function(event, pos, item) {
        $("#x").text(pos.x.toFixed(2));
        $("#y").text(pos.y.toFixed(2));

        if (item) {
            if (previousPoint != item.dataIndex) {
                previousPoint = item.dataIndex;

                $("#tooltip").remove();
                var x = item.datapoint[0].toFixed(2),
                    y = item.datapoint[1].toFixed(2);

                var t = new Date(Math.floor(x));
                var result = "正常";
                if (y == 1) {
                    result = "过低";
                } else if (y == 2) {
                    result = "较低";
                } else if (y == 4) {
                    result = "充足";
                }
                showTooltip(item.pageX, item.pageY, t.toLocaleString() + " " + item.series.label + result);
            }
        } else {
            $("#tooltip").remove();
            previousPoint = null;
        }
    });

}

function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)
        return unescape(r[2]);
    return null;
}

function gDate(arg_1) {
    arg_1.replace('-', '/');
    return new Date(arg_1);
}

function getPageSize_1() {
    page_size_1 = $('#number_gage').attr("value");
    if (page_size_1 == null || page_size_1 == "" || page_size_1 < 1) {
        page_size_1 = 10;
        $('#number_gage').val(page_size_1);
    }
}

function getPageSize_2() {
    page_size_2 = $('#number_temp').attr("value");
    if (page_size_2 == null || page_size_2 == "" || page_size_2 < 1) {
        page_size_2 = 10;
        $('#number_temp').val(page_size_2);
    }
}

function getPageSize_3() {
    page_size_3 = $('#number_level').attr("value");
    if (page_size_3 === null || page_size_3 === "" || page_size_3 < 1) {
        page_size_3 = 10;
        $('#number_level').val(page_size_3);
    }
}