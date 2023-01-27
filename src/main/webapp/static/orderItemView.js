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
	console.log(url);
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
}

function displayOrderItemList(data){
	var $tbody = $('#orderItem-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml = ' <button style ="background-color:#d11a2a" class="btn-disable btn btn-primary" onclick="deleteOrderItem('
		+ e.id + ')">delete</button>'
		+ ' <button onclick="fillFields(' + e.id + ','
		+ e.orderId + ',' + e.productId + ',' + e.quantity + ','
		+ e.sellingPrice + ')" class="btn-disable btn btn-primary" data-toggle="modal"'
		+ 'data-target="#exampleModalCenter">edit</button>';
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.orderId + '</td>'
		+ '<td>' + e.productId + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '<td>' + e.sellingPrice + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
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


function disableEditing()
{
    const buttons = document.getElementsByClassName("btn-disable");
    for (let i = 0; i < buttons.length; i++)
      buttons[i].disabled=true;
    $('#download-invoice').disabled = true;
}

function setStatus(message)
{
    document.getElementById("status").innerHTML = "status: " + message;
}

function downloadInvoice()
{
    var url = getInvoiceUrl() + "/" + orderId;
    console.log(url);
    window.location.href = url;
}

function init()
{
    const str = document.URL;
    orderId = str.split('/').pop();
    //orderId = $("meta[name=orderId]").attr("content");
    id = $("meta[name=id]").attr("content");
    customerName = $("meta[name=customerName]").attr("content");
 //   document.getElementById("inputOrderId").value = orderId;
//    document.getElementById("customer-name").innerHTML = customerName;

    status = $("meta[name=status]").attr("content");
    $('#download-invoice').click(downloadInvoice).disabled = true;
}

$(document).ready(init);
$(document).ready(getOrderItemList);