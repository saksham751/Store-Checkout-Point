function getInventoryReportUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/api/inventory-report";
}

function getInventoryReport() {
  var url = getInventoryReportUrl();
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      displayInventoryReportList(data);
    },
    error: handleAjaxError,
  });
}

//UI DISPLAY METHODS
function displayInventoryReportList(data) {
  var $tbody = $("#inventory-table").find("tbody");
  $tbody.empty();
  for (var i in data) {
    var e = data[i];
    var row =
      "<tr>" +
      "<td>" + e.brand +"</td>"
      + "<td>" + e.category + "</td>"
      + "<td>" + e.quantity +"</td>"
      +"</tr>";
    $tbody.append(row);
  }
  pagination()
}

//INITIALIZATION CODE
function init() {
  $("#refresh-data").click(getInventoryReport);
}
function pagination()
{
$('#inventory-table').DataTable();
  $('.dataTables_length').addClass('bs-select');
}
$(document).ready(init);
$(document).ready(getInventoryReport);