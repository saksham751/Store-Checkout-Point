
function getProductUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/product";
}

//BUTTON ACTIONS
function addProduct(event){
	//Set the values to update

	var $form = $("#product-form");
	var json = toJson($form);
	var url = getProductUrl();
    console.log(json);

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   		getProductList();
	   		document.getElementById("product-form").reset();
                        	   		$('#create-product-modal').modal('hide');
                                    document.getElementById('toast-container').classList.remove('bg-warning','bg-danger','bg-success');
                                    document.getElementById('toast-container').classList.add('bg-success');
                                    document.getElementById('my-message').innerHTML="The product was added successfully";
                                    $(".toast").toast('show');
	   },
	   error: handleAjaxError
	});

	return false;
}

function updateProduct(event){
	$('#edit-product-modal').modal('toggle');
	//Get the ID
	var id = $("#product-edit-form input[name=id]").val();
	var url = getProductUrl() + "/" + id;

	//Set the values to update
	var $form = $("#product-edit-form");
	var json = toJson($form);

	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   		getProductList();
	   		document.getElementById("product-edit-form").reset();
            	   		$('#create-product-modal').modal('hide');
                        document.getElementById('toast-container').classList.remove('bg-warning','bg-danger','bg-success');
                        document.getElementById('toast-container').classList.add('bg-success');
                        document.getElementById('my-message').innerHTML="The product was added successfully";
                        $(".toast").toast('show');
	   },
	   error: handleAjaxError
	});

	return false;
}


function getProductList(){
	var url = getProductUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayProductList(data);
	   },
	   error: handleAjaxError
	});
}

function deleteProduct(id){
	var url = getProductUrl() + "/" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		getProductList();
	   },
	   error: handleAjaxError
	});
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData(){
	var file = $('#productFile')[0].files[0];
	readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results){
	fileData = results.data;
	if(fileData.length > 5000)
        	{
        	    document.getElementById('status-message').innerHTML = "Data length cannot be grater than 500";
                    document.getElementById('status').style.backgroundColor = "red";
                   	$('.toast').toast('show');
                return false;
        	}
	uploadRows();
}

function uploadRows(){
	//Update progress
	updateUploadDialog();
	//If everything processed then return
	if(processCount==fileData.length){
		return;
	}

	//Process next row
	var row = fileData[processCount];
	processCount++;

	var json = JSON.stringify(row);
	var url = getProductUrl();

	//Make ajax call
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   		uploadRows();
	   },
	   error: function(response){
	   		row.error=response.responseText
	   		errorData.push(row);
	   		uploadRows();
	   		document.getElementById('download-errors').disabled=false;
	   }
	});

}

function downloadErrors(){
	writeFileData(errorData);
}

//UI DISPLAY METHODS

function displayProductList(data){
	var $tbody = $('#product-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml = '<button style ="background-color:#d11a2a" class="btn-disable btn btn-primary" onclick="deleteProduct(' + e.id + ')">Delete</button>'
		buttonHtml += ' <button class="btn-disable btn btn-primary" onclick="displayEditProduct(' + e.id + ')">Edit</button>'
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.productName + '</td>'
		+ '<td>'  + e.brand_category + '</td>'
		+ '<td>'  + e.barcode + '</td>'
		+ '<td>'  + e.mrp.toFixed(2) + '</td>'
		+ '<td class ="supervisor-view">' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
	if($("meta[name=role]").attr("content") == "operator")
                hideSupervisorView();
	pagination();
}

function displayEditProduct(id){
	var url = getProductUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayProduct(data);
	   },
	   error: handleAjaxError
	});
}

function resetUploadDialog(){
	//Reset file name
	var $file = $('#productFile');
	$file.val('');
	$('#productFileName').html("Choose File");
	//Reset various counts
	processCount = 0;
	fileData = [];
	errorData = [];
	//Update counts
	updateUploadDialog();
}

function updateUploadDialog(){
	$('#rowCount').html("" + fileData.length);
	$('#processCount').html("" + processCount);
	$('#errorCount').html("" + errorData.length);
}

function updateFileName(){
	var $file = $('#productFile');
	var fileName = $file.val();
	$('#productFileName').html(fileName);
}

function displayUploadData(){
 	resetUploadDialog();
	$('#upload-product-modal').modal('toggle');
}

function displayProduct(data){
	$("#product-edit-form input[name=productName]").val(data.productName);
	$("#product-edit-form input[name=brand_category]").val(data.brand_category);
	$("#product-edit-form input[name=id]").val(data.id);
	$('#edit-product-modal').modal('toggle');
}


//INITIALIZATION CODE
function init(){
	$('#add-product').click(addProduct);
	$('#update-product').click(updateProduct);
	$('#refresh-data').click(getProductList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
    $('#productFile').on('change', updateFileName);
    if($("meta[name=role]").attr("content") == "operator"){
        document.getElementById('supervisor-view').style.display= 'none';
    }
}
function pagination()
{
    $('#product-table').DataTable();
    $('.dataTables_length').addClass('bs-select');
}
$(document).ready(init);
$(document).ready(getProductList);

