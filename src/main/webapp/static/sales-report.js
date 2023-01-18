var salesReportData;
var newBrands = {};

function getRevenueUrl(){
 	var baseUrl = $("meta[name=baseUrl]").attr("content")
 	return baseUrl + "/api/sales-report";
 }

function getBrandUrl()
{
    var baseUrl = $("meta[name=baseUrl]").attr("content")
     	return baseUrl + "/api/brand";
}

function getBrandOption() {
        selectElement = document.querySelector('#inputFilterBrand');
        output = selectElement.options[selectElement.selectedIndex].value;
        return output;
}

function getCategoryOption() {
        selectElement = document.querySelector('#inputFilterCategory');
        output = selectElement.options[selectElement.selectedIndex].value;
        return output;
}

function getRevenueList()
{
    getProductRevenueList();
    getBrandRevenueList();
    getCategoryRevenueList();
}

function getProductRevenueList(){
	var url = getRevenueUrl();
	var $form = $("#filter-date-form");
    var json = toJson($form);
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
            'Content-Type': 'application/json'
       },
	   success: function(data) {
	   console.log(data);
	   salesReportData = data;
	   		displayRevenueProductList(data);
	   },
	   error: handleAjaxError
	});
}

function getBrandRevenueList()
{
    var url = getRevenueUrl() + "-brand";
    	var $form = $("#filter-date-form");
        var json = toJson($form);
    	$.ajax({
    	   url: url,
    	   type: 'POST',
    	   data: json,
    	   headers: {
                'Content-Type': 'application/json'
           },
    	   success: function(data) {
    	   console.log(data);
    	   		displayRevenueBrandList(data);
    	   },
    	   error: handleAjaxError
    	});
}

function getCategoryRevenueList()
{
    var url = getRevenueUrl() + "-category";
    	var $form = $("#filter-date-form");
        var json = toJson($form);
    	$.ajax({
    	   url: url,
    	   type: 'POST',
    	   data: json,
    	   headers: {
                'Content-Type': 'application/json'
           },
    	   success: function(data) {
    	   console.log(data);
    	   		displayRevenueCategoryList(data);
    	   },
    	   error: handleAjaxError
    	});
}

function displayRevenueProductList(data)
{
    var $tbody = $('#product-revenue-list-table').find('tbody');
    $tbody.empty();
    for(var i in data){
    		var e = data[i];
    		var index = i+1;
    		var row = '<tr>'
    		+ '<td>' + index + '</td>'
    		+ '<td>' + e.barcode + '</td>'
    		+ '<td>'  + e.name + '</td>'
    		+ '<td>'  + e.mrp + '</td>'
    		+ '<td>'  + e.quantity + '</td>'
    		+ '<td>'  + e.total + '</td>'
    		+ '</tr>';
            $tbody.append(row);
    	}
}

function displayRevenueBrandList(data)
{
    var $tbody = $('#brand-revenue-list-table').find('tbody');
        $tbody.empty();
        for(var i in data){
        		var e = data[i];
        		var brandName = e.brand;
        		var row = '<tr onclick=displayBrandRevenue()>'
        		+ '<td>' + i+1 + '</td>'
        		+ '<td>' + e.brand + '</td>'
        		+ '<td>'  + e.quantity + '</td>'
        		+ '<td>'  + e.total + '</td>'
        		+ '</tr>';
                $tbody.append(row);
                console.log(e.brand);
        	}
}

function displayRevenueCategoryList(data)
{
    var $tbody = $('#category-revenue-list-table').find('tbody');
        $tbody.empty();
        for(var i in data){
        		var e = data[i];
        		var row = '<tr onclick = "displayCategory()">'
        		+ '<td>' + i+1 + '</td>'
        		+ '<td>' + e.category + '</td>'
        		+ '<td>'  + e.quantity + '</td>'
        		+ '<td>'  + e.total + '</td>'
        		+ '</tr>';
                $tbody.append(row);
        	}
}

function displayBrandRevenue()
{
console.log("Hi");
    console.log("this will display revenue of brand: ");
}

function displayCategory()
{
    console.log("this will display revenue of category: ");
}

function showBrandView()
{
    document.getElementById("category-div").style.display = "none";
    document.getElementById("product-div").style.display = "none";
    document.getElementById("brand-div").style.display = "block";
}

function showCategoryView()
{
    document.getElementById("category-div").style.display = "block";
    document.getElementById("product-div").style.display = "none";
    document.getElementById("brand-div").style.display = "none";
}

function showProductView()
{
    document.getElementById("category-div").style.display = "none";
    document.getElementById("product-div").style.display = "block";
    document.getElementById("brand-div").style.display = "none";
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
       Object.assign(newBrands, {"All":[]});
       for(var i in data)
       {
           var a = data[i].brand;
           var b = data[i].category;
           if(!newBrands.hasOwnProperty(a))
               Object.assign(newBrands, {[a]:[]});
           newBrands[a].push(b);
           newBrands["All"].push(b);
       }

       newBrands["All"] = removeDuplicates(newBrands["All"]);

       console.log(newBrands);

       var $elB = $("#inputFilterBrand");

       $elB.empty();

       $.each(newBrands, function(key,value) {
             $elB.append($("<option></option>")
                .attr("value", key).text(key));
           });

       displayCategoryList();
   }

function displayCategoryList()
{
    var $elC = $("#inputFilterCategory");

    $elC.empty();

    console.log("this is it");
    var a = getBrandOption();

    console.log(newBrands[a]);

    $elC.append($("<option></option>")
                    .attr("value", "All").text("All"));

    for(var i=0; i<newBrands[a].length; i++)
    {
        $elC.append($("<option></option>")
            .attr("value", newBrands[a][i]).text(newBrands[a][i]));
    }
}

function applyBrandCategoryFilter()
{
console.log("this is it");
    var brandFilter = getBrandOption();
    var categoryFilter = getCategoryOption();
    console.log(brandFilter);
    console.log(categoryFilter);
    var data = [];

    for(var i = 0; i<salesReportData.length; i++){
        if(check(salesReportData[i].brand, brandFilter) && check(salesReportData[i].category, categoryFilter))
            data.push(salesReportData[i]);
    }
    displayRevenueProductList(data);
}

// helpers

function check(a, b)
{
    if(b=="All" || a==b)
        return true;
    return false;
}

function removeDuplicates(arr) {
        return arr.filter((item,
            index) => arr.indexOf(item) === index);
}

function init()
{
    $('#show-revenue').click(getRevenueList);
    $('#product-view').click(showProductView);
    $('#brand-view').click(showBrandView);
    $('#category-view').click(showCategoryView);

    document.getElementById("category-div").style.display = "none";
    document.getElementById("product-div").style.display = "block";
    document.getElementById("brand-div").style.display = "none";

    $('#inputFilterBrand').change(displayCategoryList);
    $('#apply-brand-category-filter').click(applyBrandCategoryFilter);
}

$(document).ready(init);
$(document).ready(getBrandsList);