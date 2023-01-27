var brand ={};
function getSalesReportUrl(){
   var baseUrl = $("meta[name=baseUrl]").attr("content")
   return baseUrl + "/api/sales-report";
}

function getBrandUrl()
{
    var baseUrl = $("meta[name=baseUrl]").attr("content")
     	return baseUrl + "/api/brand";
}

function getBrandOption() {
        selectElement = document.querySelector('#inputBrand');
        output = selectElement.options[selectElement.selectedIndex].value;
        return output;
}

function getCategoryOption() {
        selectElement = document.querySelector('#inputCategory');
        output = selectElement.options[selectElement.selectedIndex].value;
        return output;
}

function filterSalesReport() {
    var $form = $("#sales-form");
    var json = toJson($form);
    var url = getSalesReportUrl();
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
            displaySalesReport(response);
       },
       error: handleAjaxError
    });
}


function getBrandsList()
{
    var url = getBrandUrl();
    $.ajax({
    	   url: url,
    	   type: 'GET',
    	   success: function(data) {
    	   		console.log(data);
    	   		displayBrandsList(data);
    	   }
    	});
   }
   function displayBrandsList(data)
   {
       Object.assign(brand, {"":[]});
       for(var i in data)
       {
           var a = data[i].brand;
           var b = data[i].category;
           if(!brand.hasOwnProperty(a))
               Object.assign(brand, {[a]:[]});
           brand[a].push(b);
           brand[""].push(b);
       }

       brand[""] = removeDuplicates(brand[""]);

       console.log(brand);

       var $elB = $("#inputBrand");

       $elB.empty();

       $.each(brand, function(key,value) {
             $elB.append($("<option></option>")
                .attr("value", key).text(key));
           });

       displayCategoryList();
   }

function displayCategoryList()
{
    var $elC = $("#inputCategory");

    $elC.empty();

    console.log("this is it");
    var a = getBrandOption();

    console.log(brand[a]);

    $elC.append($("<option></option>")
                    .attr("value", "").text(""));

    for(var i=0; i<brand[a].length; i++)
    {
        $elC.append($("<option></option>")
            .attr("value", brand[a][i]).text(brand[a][i]));
    }
}

//function applyBrandCategoryFilter()
//{
////console.log("this is it");
//    var brandFilter = getBrandOption();
//    var categoryFilter = getCategoryOption();
//    console.log(brandFilter);
//    console.log(categoryFilter);
//    var data = [];
//
//    for(var i = 0; i<productRevenueData.length; i++){
//        if(check(productRevenueData[i].brand, brandFilter) && check(productRevenueData[i].category, categoryFilter))
//            data.push(productRevenueData[i]);
//    }
//    displaySalesReport(data);
//}

// helpers

function check(a, b)
{
    if(b=="" || a==b)
        return true;
    return false;
}
function removeDuplicates(arr) {
        return arr.filter((item,
            index) => arr.indexOf(item) === index);
}
function displaySalesReport(data) {
    var $tbody = $('#sales-table').find('tbody');
    $tbody.empty();
    for(var i in data){
        let srNo = Number.parseInt(i) + 1
        var b = data[i];
        var row = '<tr>'
        + '<td>'+srNo+'</td>'
        + '<td>' + b.brand + '</td>'
        + '<td>' + b.category + '</td>'
        + '<td>' + b.quantity + '</td>'
        + '<td>' + b.total + '</td>'
        + '</tr>';
        $tbody.append(row);
    }

    pagination();
}
function downloadSalesReport() {
    var $form = $("#sales-form");
    var json = toJson($form);
    var url = getSalesReportUrl();
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
function downloadTSV(response){
	writeFileData(response);
}
//INITIALIZATION CODE
function init(){
   $('#filter-sales-report').click(filterSalesReport);
   $('#inputBrand').change(displayCategoryList);
   $('#download-sales-report').click(downloadSalesReport);

}
function pagination()
{
$('#sales-table').DataTable();
  $('.dataTables_length').addClass('bs-select');
}
//$(document).ready(function () {
//  $('#sales-table').DataTable();
//  $('.dataTables_length').addClass('bs-select');
//});
$(document).ready(init);
$(document).ready(getBrandsList);
