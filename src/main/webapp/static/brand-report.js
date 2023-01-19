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
  var $tbody = $("#brandCategory-table").find("tbody");
  $tbody.empty();
  for (var i in data) {
    var e = data[i];
    var row =
      "<tr>" +
      "<td class='text-center'>" +
      e.brand +
      "</td>" +
      "<td class='text-center'>" +
      e.category +
      "</td>" +
      "</tr>";
    $tbody.append(row);
  }
}

//INITIALIZATION CODE
function init() {
  $("#refresh-data").click(getbrandReport);
}

$(document).ready(init);
$(document).ready(getbrandReport);