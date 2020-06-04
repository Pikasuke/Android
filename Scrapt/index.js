//import de librairie require
const axios = require("axios");
const cheerio = require("cheerio");
//Def des variable
const urlToScrap = "https://latindancecalendar.com/festivals/location/europe/style/bachata/";
const pageToScrap = [];
const allFbLinks = [];
// utilisation de la librairie

/* const getMyPage = () => {
    // on appel la page via axios, qui nous renvoie une promesse, 
    //on applique donc un then pour attendre la reponse afin d'obtenir la page entiere apres chargement
    // que l'on stock ds $
    const pageResult = axios
        .get(urlToScrap)
        .then(response => {
            console.table(response);
            console.log(pageResult);
            const $ = cheerio.load(pageResult);
        });
}  */

const getMyPage = async (url) => {
    //utilisation du mot clefs async afin de retourner une fct synchrone qui va etre une promesse 
    // avec le await on attends le resultat de la ligne courante avt de passer à la suivante
    // la page web se trouve ds la variable data de l'objet page
    const pageResult = await axios.get(url);
    const $ = cheerio.load(pageResult.data);
    return $;
}
//ne pas oublier lors de l'appel de la fct async qu'elle await sur so nthread et pas le principal donc 
/*  const docToScrapt = await getMyPage(urlToScrap);  */

// Donc creer une fonction ifi qui s'appelle tt seule pour quelle passe en async pour pouvoir la await

(async () => {
    const $docToScrapt = await getMyPage(urlToScrap);
    // on inspecte l'objet et on recupere ce qui nous interesse via les selecteur j query 
    // console.log($docToScrapt)
    // on obtient 
    //$docToScrapt("#primary .eventline.event_details a.url.summary")
    // on recupere un tableau sur lequel on for each pour individualiser les element

    $docToScrapt("#primary .eventline.event_details a.url.summary").each((monIdex, aTagSouaite) => {
        // console.log(aTagSouaite.attribs.href)
        pageToScrap.push(aTagSouaite.attribs.href)
    });

    console.table(pageToScrap)

    /*  const $pageLien = await getMyPage(pageToScrap[0]);
    // recherche du lien fb dans la nouvelle page
    const fbLink = $pageLien(".vevent a.outbound.festival.fb, .vevent a.event_link").attr("href")
    console.log(fbLink);

    */
    // on creer une promesse pour le soucis de timer d'appel serveur
    const myPromise = new Promise( async (resolve, reject) => {
        const $pageLien = await getMyPage(pageToScrap[0]);
        const fbLink = $pageLien(".vevent a.outbound.festival.fb, .vevent a.event_link").attr("href")
        resolve(fbLink)
    });

    const result = await myPromise;
    allFbLinks.push(result);
    console.log(allFbLinks)



})();


