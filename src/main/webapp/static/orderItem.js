var orderId;
var orderCode
var status;
var customerName;
var updateId;
function getStoreUrl(){
 	var baseUrl = $("meta[name=baseUrl]").attr("content")
 	return baseUrl + "/api/orderItem";
 }

function getOrderUrl(){
    var baseUrl = $("meta[name=baseUrl]").attr("content")
 	return baseUrl + "/api/order/place";
}
 function getInvoiceUrl(){
  	var baseUrl = $("meta[name=baseUrl]").attr("content")
  	return baseUrl + "/api/invoice";
  }
function getOrderItemList(){
	var url = getStoreUrl();
	url += "/order-code/" + orderId;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   console.log(data);
	   		displayOrderItemList(data);
	   },
//	   error: handleAjaxError
	});
	pagination()
}

function displayOrderItemList(data){
	var $tbody = $('#orderItem-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml = ' <button style ="background-color:#d11a2a" class="btn-disable btn btn-primary" onclick="deleteOrderItem('
		+ e.id + ')">Delete</button>'
		buttonHtml+= ' <button onclick="fillFields(' + e.id + ','
		+ e.orderId + ',' + e.productId + ',' + e.quantity + ','
		+ e.sellingPrice + ')" class="btn-disable btn btn-primary" data-toggle="modal"'
		+ 'data-target="#exampleModalCenter">Edit</button>';
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.orderId + '</td>'
		+ '<td>' + e.productId + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '<td>' + e.sellingPrice + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
	pagination();
	if(status === "Placed")
	    disableEditing();
	if(data.length == 0)
	    document.getElementById('place-order').disabled = true;
	else
	    document.getElementById('place-order').disabled = false;
}

function addOrderItem(event)
{
    var $form = $("#orderItem-form");
    var json = toJson($form);
    var url = getStoreUrl();
    console.log(json);
    $.ajax({
    	   url: url,
    	   type: 'POST',
    	   data: json,
    	   headers: {
           	'Content-Type': 'application/json'
           },
    	   success: function(response) {
    	   		getOrderItemList();
    	   		document.getElementById('toast-container').classList.remove('bg-warning','bg-danger','bg-success');
                                       document.getElementById('toast-container').classList.add('bg-success');
                                       document.getElementById('my-message').innerHTML="Item Added";
                                       $(".toast").toast('show');
    	   },
   	   error: handleAjaxError
//            error: setStatus(response)

    	});
    	return false;
}

async function placeOrder()
{
    var url = getOrderUrl() + "/" + orderId;
    $.ajax({
        	   url: url,
        	   type: 'PUT',
        	   headers: {
               	'Content-Type': 'application/json'
               },
        	   success: function(response) {
        	   console.log("order placed");
        	   		downloadInvoice();
        	   },
    //    	   error: handleAjaxError
//                error: setStatus(response)

        	});
}

function deleteOrderItem(id)
{
    var url = getStoreUrl() + "/" + id;

    	$.ajax({
    	   url: url,
    	   type: 'DELETE',
    	   success: function(data) {
    	   		getOrderItemList();
    	   },
//    	   error: handleAjaxError
    	});
}

function fillFields(id, orderId, productId, quantity, sellingPrice)
{
console.log("filling the update order item form fields");
//    document.getElementById("inputUpdateOrderItemId").value = id;
    document.getElementById("inputUpdateOrderId").value = orderId;
    document.getElementById("inputUpdateProductId").value = productId;
    document.getElementById("inputUpdateQuantity").value = quantity;
//    document.getElementById("inputUpdateMrp").value = sellingPrice;
    updateId=id;
}
//
function updateOrderItem()
{
    var $form = $("#editOrderItemForm");
    var json = toJson($form);
    var url = getStoreUrl();
    url+= "/"+updateId;
    if((JSON.parse(json).quantity) == 0)
    {
        deleteOrderItem(JSON.parse(json).id);
        return;
    }

    $.ajax({
        	   url: url,
        	   type: 'PUT',
        	   data: json,
        	   headers: {
               	'Content-Type': 'application/json'
               },
        	   success: function(response) {
        	   		getOrderItemList();
        	   },
    //    	   error: handleAjaxError

        	});
}

function disableEditing()
{
    const buttons = document.getElementsByClassName("btn-disable");
    for (let i = 0; i < buttons.length; i++)
      buttons[i].disabled=true;

    document.getElementById('add-Item').disabled = true;
    document.getElementById('place-order').disabled = true;
    $('#download-invoice').disabled = true;
}

function setStatus(message)
{
    document.getElementById("status").innerHTML = "status: " + message;
}
async function downloadInvoice()
{
    var url = getInvoiceUrl() + "/" + orderId;
    console.log(url);
    window.location.href = url;
    window.setTimeout(() => {
      redirect();
    }, 2000);

}
async function redirect(){
    window.location= $("meta[name=baseUrl]").attr("content") + "/ui/orders";
}
function init()
{
    const str = document.URL;
    orderId = str.split('/').pop();
    //orderId = $("meta[name=orderId]").attr("content");
    id = $("meta[name=id]").attr("content");
    customerName = $("meta[name=customerName]").attr("content");
    document.getElementById("inputOrderId").value = orderId;
//    document.getElementById("customer-name").innerHTML = customerName;

    status = $("meta[name=status]").attr("content");

    $('#add-Item').click(addOrderItem);
    $('#place-order').click(placeOrder);
    $('#update-orderItem').click(updateOrderItem);
    if($("meta[name=role]").attr("content") == "operator"){
            document.getElementById('supervisor-view').style.display= 'none';
        }
}
function pagination()
{
   $('#orderItem-table').DataTable();
   $('.dataTables_length').addClass('bs-select');
}
$(document).ready(init);
$(document).ready(getOrderItemList);