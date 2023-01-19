function getbrandReportUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/api/brand-report";
}

function getbrandReport() {
  var url = getbrandReportUrl();
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      console.log(data);
      displaybrandReportList(data);
    },
    error: handleAjaxError,
  });
}

//UI DISPLAY METHODS
function displaybrandReportList(data) {
  var $tbody = $("#brand-table").find("tbody");
  $tbody.empty();
  for (var i in data) {
    var e = data[i];
    var row =
      "<tr>" +
      "<td>" + e.brand + "</td>" +
      "<td>" + e.category + "</td>" +
      "</tr>";
    $tbody.append(row);
  }
  pagination()
}

//INITIALIZATION CODE
function init() {
  $("#refresh-data").click(getbrandReport);
}
function pagination()
{
$('#brand-table').DataTable();
  $('.dataTables_length').addClass('bs-select');
}

$(document).ready(init);
$(document).ready(getbrandReport);