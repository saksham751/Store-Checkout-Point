var brand ={};
var data;
function getDaySalesUrl(){
   var baseUrl = $("meta[name=baseUrl]").attr("content")
   return baseUrl + "/api/pos_day_sales_report";
}

function filterDaySales() {
    var $form = $("#daySales-form");
    var json = toJson($form);
    var url = getDaySalesUrl();
//    console.log(url);
    console.log(json);
    $.ajax({
       url: url,
       type: 'POST',
       data: json,
       headers: {
        'Content-Type': 'application/json'
       },
       success: function(response) {
//            console.log(response);
            data =response;
            displayDaySales(response);
       },
       error: handleAjaxError
    });
}


function displayDaySales(data) {
    var $tbody = $('#daySales-table').find('tbody');
    $tbody.empty();
    for(var i in data){
        let srNo = Number.parseInt(i) + 1
        var b = data[i];
        var row = '<tr>'
        + '<td>'+srNo+'</td>'
        + '<td>' + b.date + '</td>'
        + '<td>' + b.invoicedItemsCount  + '</td>'
        + '<td>' + b.invoicedOrderCount+ '</td>'
        + '<td>' + b.totalRevenue + '</td>'
        + '</tr>';
        $tbody.append(row);
    }
    pagination();

}
function downloadDaySales() {
    var $form = $("#daySales-form");
    var json = toJson($form);
    var url = getDaySalesUrl();
//    console.log(url);
    console.log(json);
    $.ajax({
       url: url,
       type: 'POST',
       data: json,
       headers: {
        'Content-Type': 'application/json'
       },
       success: function(response) {
//            console.log(response);
            downloadTSV(response);
       },
       error: handleAjaxError
    });
}
function downloadTSV(){
	writeFileData(data);
}
//INITIALIZATION CODE
function init(){
   $('#filter-posDay-report').click(filterDaySales);
   $('#download-posDay-report').click(downloadDaySales);

}
function pagination()
{
   $('#daySales-table').DataTable();
   $('.dataTables_length').addClass('bs-select');
}

$(document).ready(init);
