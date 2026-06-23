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
	});
}
	
function cleartheme(){
	document.body.classList.remove("theme_pacient", "theme_doctor", "theme_secretary", "theme_user")
}

function changeform(){
	const perfil = document.getElementById("perfil");
	const formtitle = document.getElementById("Titulo");
	const special_feld = document.getElementById("special_part");
	const special = document.getElementById("special");
	
	if(!perfil || !formtitle || !special_feld || !special){
		return;
	}
	
	cleartheme();
	
	if(perfil.value == "PACIENTE"){
		formtitle.textContent = "New Pacient";
		special_feld.styleDisplay = "none";
		special.required = false;
		special.value = "";
		document.body.classList.add("theme_pacient");
	}else if(perfil.value == "MEDICO"){
			formtitle.textContent = "New Doctor";
			special_feld.styleDisplay = "block";
			special.required = true;
			document.body.classList.add("theme_doctor");
		}else if(perfil.value == "SECRETARIA"){
				formtitle.textContent = "New Secretary";
				special_feld.styleDisplay = "none";
				special.required = false;
				special.value = "";
				document.body.classList.add("theme_secretary");
			}else{
				formtitle.textContent = "New Pacient";
				special_feld.styleDisplay = "none";
				special.required = false;
				special.value = "";
				document.body.classList.add("theme_user");
			}
		}
	}
}

function changelistname(){
	const typepage = document.getElementById("tipoPagina");
	const titlelist = document.getElementById("Titulo");
	const subtitle = document.getElementById("Subtitulo");
	
	if(!typepage || !subtitle){
		return;
	}
	
	cleartheme();
	
	if(typepage.value == "Pacient"){
		titlelist.textContent = "List Pacients"
		
		if(subtitle){
			subtitle.textContent = "Registered Pacients of the Clinic";
		}
		
		document.body.classList.add("theme_pacient");
	} else if(typepage.value == "Medico"){
		titlelist.textContent = "List Doctors"
		
		if(subtitle){
			subtitle.textContent = "Registered Doctors of the Clinic";
		}
		
		document.body.classList.add("theme_doctor");
		} else if(typepage.value == "Secretaria"){
			titlelist.textContent = "List Secretary"
			
			if(subtitle){
				subtitle.textContent = "Registered Secretaries of the Clinic";
			}
			
			document.body.classList.add("theme_secretary");
			} else{
				titlelist.textContent = "List"
			
				if(subtitle){
					subtitle.textContent = "Registered of the Clinic";
				}
				
				document.body.classList.add("theme_user");
			}
}

window.onload = function(){
	changeform();
	changelistname();
}
