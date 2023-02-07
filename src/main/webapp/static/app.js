console.log($("meta[name=role]").attr("content"))
//HELPER METHOD
function toJson($form){
    var serialized = $form.serializeArray();
    console.log(serialized);
    var s = '';
    var data = {};
    for(s in serialized){
        data[serialized[s]['name']] = serialized[s]['value']
    }
    var json = JSON.stringify(data);
    return json;
}


function handleAjaxError(response){
	document.getElementById('toast-container').classList.remove('bg-warning','bg-danger','bg-success');
    document.getElementById('toast-container').classList.add('bg-danger');
    var myResponse = JSON.parse(response.responseText);
   	document.getElementById('my-message').innerHTML=myResponse.message;
    $(".toast").toast('show');
}

function readFileData(file, callback){
	var config = {
		header: true,
		delimiter: "\t",
		skipEmptyLines: "greedy",
		complete: function(results) {
			callback(results);
	  	}	
	}
	Papa.parse(file, config);
}


function writeFileData(arr){
	var config = {
		quoteChar: '',
		escapeChar: '',
		delimiter: "\t"
	};
	
	var data = Papa.unparse(arr, config);
    var blob = new Blob([data], {type: 'text/tsv;charset=utf-8;'});
    var fileUrl =  null;

    if (navigator.msSaveBlob) {
        fileUrl = navigator.msSaveBlob(blob, 'download.tsv');
    } else {
        fileUrl = window.URL.createObjectURL(blob);
    }
    var tempLink = document.createElement('a');
    tempLink.href = fileUrl;
    tempLink.setAttribute('download', 'download.tsv');
    tempLink.click(); 
}
function hideSupervisorView()
{
//    var appBanners = document.getElementsByClassName('supervisor-view');
//
//    for (var i = 0; i < appBanners.length; i ++) {
//        appBanners[i].style.display = 'none';
//    }
//    if($("meta[name=role]").attr("content") == "operator"){
            const collection=document.getElementsByClassName('supervisor-view');
            for (let i = 0; i < collection.length; i++) {
              collection[i].style.display= 'none';
            }
//        }
}

function init(){
    if($("meta[name=role]").attr("content") == "operator"){
            hideSupervisorView();
            }
}

$(document).ready(init);