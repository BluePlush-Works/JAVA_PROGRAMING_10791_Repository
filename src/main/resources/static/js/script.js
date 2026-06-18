function message(){
	alert("Success!")
}

function hide_show(){
	const tabela = document.getElementById("table_values");
	
	if(tabela.style.display==="none"){
		tabela.style.display="tabela";
	}else{
		tabela.style.display="none";
	}
}

function highlight(){
	const nomes= document.querySelectorAll(".value_name");
	
	nomes.forEach(function(nome){
		nome.classList.toggle("aura_read");
	})
}
