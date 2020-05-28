'use strict';
$(()=>{
    console.log($('#pseudo').val().trim);
/**
onclick => click
ondblick => dblick
onsubmit => submit
*/
	$('#formulaire').submit(function (event){
		event.preventDefault();
		
//vérifier l'existence des class pseudo et mdp...
 let noPseudo = false;
// if(noPseudo){
//     $('#pasPseudo').css('display, block');
// } else {
//     $('#pasPseudo').css('display, none');
// }

		if($('#pseudo').val().trim() == "")
		{
            $(pseudo).css('border','1px solid red');
            $('#pasPseudo').css('display','block');
			noPseudo=true;
		}else{
            $(pseudo).css('border','0px');
            $('#pasPseudo').hide();
			noPseudo=false;
		}


		if($("#mdp").val().trim() == "")
		{
			$("#mdp").css('border','1px solid red');
			$("#mdp").after('<span id="noSpan">Veuillez entrer votre mdp fdp svp</span>');
		}else{
            $("#mdp").css('border','0px');
			$('#noSpan').hide();
		}

		if(erreur === false)
		{
			$(this).before('<div class="alert alert-success">Tous les champs sont remplis</div>');
		}
	});
});
