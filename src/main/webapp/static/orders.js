function getStoreUrl(){
 	var baseUrl = $("meta[name=baseUrl]").attr("content")
 	return baseUrl + "/api/order";
 }

function getOrderList(){
	var url = getStoreUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   console.log(data);
	   		displayOrderList(data);
	   },
//	   error: handleAjaxError
	});
}

function getOrderListByDateFilter()
{
console.log("filtered orders");
    var url = getStoreUrl() + "/date-filter";
    var $form = $("#date-filter-form");
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
    	   		displayOrderList(data);
    	   },
    //	   error: handleAjaxError
    	});
}

function displayOrderList(data){
 	var $tbody = $('#order-table').find('tbody');
 	$tbody.empty();
 	for(var i in data){
 		var e = data[i];
 		var buttonHtml = ' <button class="btn btn-secondary" onClick=redirect("'+ e.id +'")>Open</button>'
 		var viewButtonHtml = ' <button class="btn btn-secondary" onClick=redirect2("'+ e.id +'")>View</button>'
 		if(e.status==="Placed"){
 		    var row = '<tr>'
             		+ '<td>' + e.id + '</td>'
             		+ '<td>' + e.time + '</td>'
             		+ '<td>' + e.status + '</td>'
             		+ '<td>' + viewButtonHtml + '</td>'
             		+ '<td>'+ ""+ '</td>'
             		+ '</tr>';
            $tbody.append(row);
 		}else{
            var row = '<tr>'
            + '<td>' + e.id + '</td>'
            + '<td>' + e.time + '</td>'
            + '<td>' + e.status + '</td>'
            + '<td>' + buttonHtml + '</td>'
            + '</tr>';
             $tbody.append(row);
         }
 	}
 	pagination();
 }

function redirect(id)
{
//    console.log(status);
//    if(status==="placed") return;
    window.location.href = $("meta[name=baseUrl]").attr("content") + "/ui/orderItem/" + id;
}
function redirect2(id)
{
//    console.log(status);
//    if(status==="placed") return;
    window.location.href = $("meta[name=baseUrl]").attr("content") + "/ui/orderItemView/" + id;
}

 function createOrder(event)
 {
     var $form = $("#order-form");
     var json = toJson($form);
     var url = getStoreUrl();

     $.ajax({
     	   url: url,
     	   type: 'POST',
     	   data: json,
     	   headers: {
            	'Content-Type': 'application/json'
            },
     	   success: function(response) {
     	   		getOrderList();

     	   		//setStatus(response);
     	   },
 //    	   error: handleAjaxError
 //            error: setStatus(response)

     	});
     	return false;
 }
function disableEditing()
{
    const buttons = document.getElementsByClassName("btn-disable");
    for (let i = 0; i < buttons.length; i++)
      buttons[i].disabled=true;

    document.getElementById('add-Item').disabled = true;
    document.getElementById('place-order').disabled = true;
    $('#download-invoice').disabled = false;

    document.getElementById("customerName").readOnly = true;
}
 function setStatus(message)
 {
     document.getElementById("status").innerHTML = "status: " + message;
 }

 function init()
 {
    $('#create-order').click(createOrder);
    $('#apply-date-filter').click(getOrderListByDateFilter);
 }
 function pagination()
 {
    $('#order-table').DataTable();
    $('.dataTables_length').addClass('bs-select');
 }

 $(document).ready(init);
 $(document).ready(getOrderList);