var fullName;
var studentNumber;
var loginName;
var program;
var homeCountry;
var aListType = new Array();
var aList = new Array();

class Animal{
  constructor (name, type, legs, photo, ...colors){
    this.name = name; 
    this.type = type;
    this.legs = legs;
    this.colors = colors;
    this.photo = photo;
  } //End of constructor()
} //End of Animal class

$(document).ready(function() {
  $.ajax({
    type:"GET",
    url:"data/A2-JSONFile.json",
    dataType: "json",
    success: loadFile,
    error: function(e) {alert(`${e.status} - ${e.statusText}`);}
  }); //End of .ajax()
}); //End of doc.ready()

function loadFile(data){
  fullName = data.personalInfo.fullName;
  studentNumber = data.personalInfo.studentNumber;
  loginName = data.personalInfo.loginName;
  program = data.personalInfo.sheridanProgram;
  homeCountry = data.personalInfo.homeCountry;

  for (let a of data.animallist) {
    var colors = new Array();
    for(let color of a.colors){
      colors.push(color);
    } //End of foreach color loop

    aList.push(new Animal(a.name, a.type, a.legs, a.photoUrl, a.colors));
  } //End of foreach animalList loop

  for (let aType of data.animaltypes) {
    aListType.push(aType.atype, aType.pic);
  } //End of foreach animalType loop

  mainScreen();
} //End of loadFile()

$(document).on("click", ".info > a", function() {
  localStorage.setItem("fullName", fullName);
  localStorage.setItem("studentNumber", studentNumber);
  localStorage.setItem("loginName", loginName);
  localStorage.setItem("program", program);
  localStorage.setItem("homeCountry", homeCountry);
  localStorage.setItem("animalList", JSON.stringify(aList));
  localStorage.setItem("animalType", JSON.stringify(aListType));
  localStorage.setItem("rowID", $(this).closest("a").attr("id"));
}); //End of doc.on(click)

function mainScreen(){
  //Header information
  $("#intro").html(`<h3>Assignment #2<h3>`);
  $("#intro").append(`${fullName} / ${studentNumber} / ${loginName}<br>`);
  $("#intro").append(`<hr>`);
  $("#intro").addClass("alignClass");

  //Data text
  $("#text").html(`<p>Click a button to display all animals for the chosen type:</p><br>`);
  $("#text").addClass("alignClass");

  //Data div
  for (let index = 0; index<aListType.length/2; index++) {
    $("#data").append(
      `
      <div class="info">
        <a id="${index}" href="otherPages/nextPage.html">
          <div class="buttons">
            ${aListType[index*2]}
          </div>
        </a>
        <img width="100" src="images/${aListType[index*2+1]}"/>
      </div>
    `
    );
  }
  $("#data").addClass("alignClass");

  //Footer information
  $("#signature").html(`<hr>`);
  $("#signature").append(`${program}<br>`);
  $("#signature").append(`${homeCountry}`);
  $("#signature").addClass("alignClass");
  $("#signature").css("margin-top","20px");
} //End of mainScreen()