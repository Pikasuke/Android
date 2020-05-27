'use strict';

//Vérification formulaire :
let monForm = document.querySelector('form'),
    prenom = document.getElementById('prenom'),
    nom = document.getElementById('nom'),
    email = document.getElementById('email'),
    message = document.getElementById('message'),
    envoyer = document.getElementById('envoyer');


function verifChamp(field, min, max) {
    if (field.value == "") {
        console.log(field.nextElementSibling);
        field.nextElementSibling.classList = "alert alert-danger d-block w-100";
        field.nextElementSibling.textContent = "Merci de remplir ce champ";
        field.style.borderColor = "red";
    } else if (field.value.length < min && field.value.length > max) {
        console.log(field.nextElementSibling);
        field.nextElementSibling.classList = "alert alert-danger";
        field.nextElementSibling.textContent = "La longueur doit être comprise entre " + min + "caractères et " + max + "caractères";
        field.style.borderColor = "red";
    } else { //s'il n'y a pas d'erreurs
        console.log(field.nextElementSibling);
        field.nextElementSibling.classList = "";
        field.nextElementSibling.textContent = "";
        field.style.borderColor = "rgb(169, 169, 169)";
    }
}

//onsubmit
monForm.onsubmit = function (event) {

    //	alert('click effectué');
    event.preventDefault();//je désactive le comportement natif du submit
    verifChamp(prenom, 5, 20);
    verifChamp(nom, 2, 40);
    verifChamp(email, 5, 100);
    verifChamp(message, 50, 200);
    //console.dir(event);
}

//Caroussel, je creer un tableau dans lequel on stock plusieurs image
//L'appui sur les fleches incremente l'indice du tableau
const sliderTab = ["https://via.placeholder.com/500x350?text=Slide%201", "https://via.placeholder.com/500x350?text=Slide%202", "https://via.placeholder.com/500x350?text=Slide%203", "https://via.placeholder.com/500x350?text=Slide%204"];
console.log(document.querySelectorAll('.changeSlide'));
//1 regarder si ça fonctionne ac console.log
//2 parcours le tableau
let num = 0;

document.querySelectorAll('.changeSlide').forEach(monElement => {
monElement.onclick = function (e) {
    // à mon selecteur document.querySelectorAll('.changeSlide') j'applique une fonction foreach 
    // auquel à chaque element j'applique une fonction onclick que je declare ds une fct anomyne = function(e)
    // faire attention au crochet et parenthese; 
    e.preventDefault();
    console.dir(e);
    //this represente le slide
    let sens = 0;
    //j'identifie la fleche gauche ac un decrement et la droite avec un increment
    (this.id == "suivant") ? sens++ : sens--;
    console.log(sens);
    // on utilise une arrow fonction qd on n'utilise pas l'objet courant this qui devient automatiquement windows

    //calcul des borne inf et sup du carroussel

    //1er tour de boucle j'appuis sur gauche   0 = 0 + -1 donc num <0 num = 3
    num = num + sens;
    if (num < 0) {
        num = sliderTab.length - 1;
    }
    if (num > sliderTab.length - 1) num = 0;
    //j'affecte & mon document le tableau avec le bon indice num
    document.getElementById('slide').src = sliderTab[num];
}
});


// [].forEach.call(document.querySelectorAll('.changeSlide'), slide => {
//     //arrow fonction on garde contexte de base vs fonction on peut changer de context qui est egale à celui de la fonction
//     // la fonction anomyme permet possede le parametre e = à slide donc son this est slide
//     //à chaque click j'effectue une fonction anomyne ou arrow fonction 


//     slide.onclick = function(e) {
//         // je desactive le comportement par defaut
//         e.preventDefault();
//         console.dir(e);
//         //this represente le slide
//         let sens = 0;
//         //j'identifie la fleche gauche ac un decrement et la droite avec un increment
//         (this.id == "suivant") ? sens++ : sens--;
//         console.log(sens);
//         // on utilise une arrow fonction qd on n'utilise pas l'objet courant this qui devient automatiquement windows

//         //calcul des borne inf et sup du carroussel

//         //1er tour de boucle j'appuis sur gauche   0 = 0 + -1 donc num <0 num = 3
//         num = num + sens;
//         if (num <0) {
//             num = sliderTab.length - 1;
//         }
//         if (num>sliderTab.length-1) num = 0;
//         //j'affecte & mon document le tableau avec le bon indice num
//         document.getElementById('slide').src = sliderTab[num];
//     }
// });

//SCROLL :
// monter

let goToTop = document.getElementById('haut')
let goToBot = document.getElementById('bas')
let stopAnim = document.getElementById('stop')
// on test si les elements st bien identifier ds le console log on recupere l'element this le div html
// declare une variable qui va stocker une action;
let toto = 6, action;

goToTop.onclick = function () {
    console.log(this);
    //à la variable je lui affecte un settimout qui va executer un appel de fct pdt x mls
    monter();

}
goToBot.onclick = function () {
    console.log(this);
    console.log(goToBot);
    descendre();
}
stopAnim.onclick = function () {
    console.log(this);
    clearTimeout(action);
}


let monter = function () {
    scrollBy(0, -1);
    action = setTimeout(monter, 10);
};
let descendre = function () {
    scrollBy(0, 1);
    action = setTimeout(descendre, 10);
}
let stop = function () {

}

//redimensionner l'image

const fulu = document.getElementById('fulu');

const range = document.querySelector("input[type='range']");

//est appelé 1 fois car le script est chargé plusieurs
// console.log(range.value);
// const testValue = function (monEvent){
//     console.log(range)
//     console.log(range.value);
//     console.log(monEvent)
//     //console.log(monEvent.target)
// }
// testValue();
// range.addEventListener("click", testValue )

//defini la fct qui va etre appeler par addevenlister
//monEvenement le parametre de la fct est un evenment qui ne peut qu'etre donné par addEventlistener (ds ce cas)
const redimensioneToi = function (monEvenement) {
    fulu.style.width = monEvenement.target.value + "%";
}

range.addEventListener("change", redimensioneToi)


//MOUSE MOUVEMENT
//identifier l'element
const sVert = document.getElementById('sVerte');
//j'affecte 'evenement mouseenter à mon element et 
//je lui demande de faire l'action background
sVert.addEventListener("mouseenter", () => {
    sVert.style.background = "green";
});

sVert.addEventListener("mouseleave", () => {
    sVert.style.background = "transparent";
});

///MOUSE MOUVEMENT SUR UN GROUPE DE SELECTOR

const allSouris = document.getElementsByClassName('souris');
//je boucle ac chaque souris sur mon tableau de souris 
for (const oneSouri of allSouris) {
    oneSouri.addEventListener("mouseenter", () => {
        oneSouri.style.color = "white";
    });
    oneSouri.addEventListener("mouseleave", () => {
        oneSouri.style.color = "black";
    });
}

Object.values(allSouris).forEach(el => console.log(el))

// allSouris.forEach(oneSouri => { 
// console.log("toto");
// });
console.log(allSouris);
const yolo = () => document.querySelectorAll('.souris').forEach(oneSouri => {
    console.log("tutu");
});

yolo();


// Methode lisible qiui equivaut aux 2 suivantes
const maFonction = function (oneSouri) {
    console.log("1er")
}
document.querySelectorAll('.changeSlide').forEach(maFonction);
// Fonction anonyme ac changement possible de contexte "this"
document.querySelectorAll('.changeSlide').forEach(function (oneSouri) {
    console.log("1er")
});
// Arrow function = fct anonyme gardant le contexte "this" de base (window si navigateur)
document.querySelectorAll('.changeSlide').forEach((oneSouri) => {
    console.log("2em")
});

// number=[1,2,3,4,5]; Arrays
// Array.prototype.forEach.call(number,function(elem){ });
// // Equivalent à
// number.forEach(function(elem){ });
// La méthode querySelectorAll()  de Element renvoie une NodeList statique 
// Renvoie un objet de type tableau de tous les éléments 
// document.getElementsByClassName - Renvoie un objet de type tableau de tous les éléments 
// La méthode querySelectorAll()  de Element renvoie une NodeList statique 
// ALORS QUE
// document.getElementsByClassName - Renvoie un objet de type tableau de tous les éléments 

