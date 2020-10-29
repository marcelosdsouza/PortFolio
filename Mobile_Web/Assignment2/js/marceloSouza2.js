var aListType = new Array();
var aList = new Array();
var rowID;

$(document).ready(function() {
  aListType = JSON.parse(localStorage.getItem("animalType"));
  aList = JSON.parse(localStorage.getItem("animalList"));
  rowID = localStorage.getItem("rowID");
  individualScreen();
});

function individualScreen(){
  //Header information
  $("#intro").html(`<h3>Assignment #2<h3>`);
  $("#intro").append(`${localStorage.getItem("fullName")} / ${localStorage.getItem("studentNumber")} / ${localStorage.getItem("loginName")}<br>`);
  $("#intro").append(`<hr>`);
  $("#intro").addClass("alignClass");
  //Data text
  $("#text").html(`<h3>${aListType[rowID*2]}</h3>`);
  $("#text").addClass("alignClass");

  //Data div
  for (let counter = 0; counter < aList.length; counter++) {
    if (aList[counter].type === aListType[rowID*2].toLowerCase() ) {
      $("#data").append(`<div class="info"> <p>&emsp;Name: ${aList[counter].name} has ${aList[counter].legs} legs and a colour range of: ${aList[counter].colors}</p> <img src="${aList[counter].photo}" width="100"></div>`);
    }
  }
  $("#data").append(
    `
    <a  href="../index.html">
      <div class="buttons">
        Back
      </div>
    </a>
    `
  );
  $("#data").addClass("alignClass");

  //Footer information
  $("#signature").html(`<hr>`);
  $("#signature").append(`${localStorage.getItem("program")}<br>`);
  $("#signature").append(`${localStorage.getItem("homeCountry")}`);
  $("#signature").addClass("alignClass");
}