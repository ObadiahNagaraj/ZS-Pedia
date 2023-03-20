function send(){
	var word=document.getElementById("inputBox").value;
	var xhr=new XMLHttpRequest();
	xhr.open("POST","./Wikipedia");
	xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xhr.send("word="+word);
	xhr.onreadystatechange=function(){
		if(this.readyState==4 && this.status==200){
			if(this.responseText=="sorry"){
				document.getElementById("cont").innerText="Sorry, we are unable to fetch the data.Please check your input.";
			}else{
				document.getElementById("content").innerHTML=this.responseText;
			}
		}
	}
}
function sen(a){
	document.getElementById("inputBox").value=a.split(",")[0];
	send();
}