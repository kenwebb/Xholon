/**
 * Scrape content from https://www.experts-exchange.com/myQuestions.jsp
 * Ken Webb
 * July 18, 2020
 */

// Parse the current page, and save the resulting file to localStorage.
var parseAndSave = function() {
var strout='<div class="amcdiv">\n';
var title=document.querySelector("h1.commentable-title").textContent;
strout+='<h2 class="amctitle">'+title+'</h2>\n';
var sdate=document.querySelector("div.submit-date>time").dateTime;
strout+='<p class="amcdate">'+sdate+'</p>\n\n';
var content=document.querySelectorAll("div.wysiwyg-content");
var str='';
content.forEach((item,index) => {str+='<pre class="amctext">'+item.textContent.trim()+'</pre>\n\n';});
strout+=str;
var files=document.querySelectorAll("a.file-inline");
var str2='<p class="amcfiles">FILES:</p>\n';
files.forEach((item) => {str2+='<p class="amcfile">'+item.title+' <a href="'+item.href+'">'+item.textContent+'</a></p>\n';});
strout+=str2;
strout+='</div>';
var filename="amc"+new Date().getTime()+title.substring(0, 29);
localStorage.setItem(filename,strout);
}

// Get all files from localStorage and write to console.
var getAll = function() {
var all = "<!doctype html><html><head><style>pre {background-color:GhostWhite;}</style></head><body>\n";
var len = localStorage.length;
for (var i = 0; i < len; i++) {
var key = localStorage.key(i);
if (key.startsWith("amc")) {all += localStorage.getItem(key) + "\n";}
}
all += "</body></html>";
localStorage.setItem("allEE",all);
}

// OLD
// bookmarklet: scrape
javascript:(function() {var strout="-----------------------------------------------------------------\n";var title=document.querySelector("h1.commentable-title").textContent;strout+=title+"\n";var sdate=document.querySelector("div.submit-date>time").dateTime;strout+=sdate+"\n\n";var content=document.querySelectorAll("div.wysiwyg-content");var str="";content.forEach((item,index) => {	str+="TEXT"+(index+1)+":\n"+item.textContent.trim()+"\n\n";});strout+=str;var files=document.querySelectorAll("a.file-inline");var str2="FILES:\n";files.forEach((item,index) => {str2+= item.title+": "+item.textContent+"\n"+item.href+"\n";});strout+=str2;var filename=new Date().getTime()+title.substring(0,29);localStorage.setItem(filename,strout);})();

// bookmarklet: show
javascript:(function() {var len=localStorage.length;var all="";for(var i=0;i<len;i++){var key=localStorage.key(i);all+=localStorage.getItem(key)+"\n";};localStorage.setItem("allEE",all);})();

// NEW
// bookmarklet: scrape
javascript:(function() {var strout='<div class="amcdiv">\n';var title=document.querySelector("h1.commentable-title").textContent;strout+='<h2 class="amctitle">'+title+'</h2>\n';var sdate=document.querySelector("div.submit-date>time").dateTime;strout+='<p class="amcdate">'+sdate+'</p>\n\n';var content=document.querySelectorAll("div.wysiwyg-content");var str='';content.forEach((item,index) => {str+='<pre class="amctext">'+item.textContent.trim()+'</pre>\n\n';});strout+=str;var files=document.querySelectorAll("a.file-inline");var str2='<p class="amcfiles">FILES:</p>\n';files.forEach((item) => {str2+='<p class="amcfile">'+item.title+' <a href="'+item.href+'">'+item.textContent+'</a></p>\n';});strout+=str2;strout+='</div>';var filename="amc"+new Date().getTime()+title.substring(0, 29);localStorage.setItem(filename,strout);})();

// bookmarklet: show
javascript:(function() {var all = "<!doctype html><html><head><style>pre {background-color:GhostWhite;}</style></head><body>\n";var len = localStorage.length;for (var i = 0; i < len; i++) {var key = localStorage.key(i);if (key.startsWith("amc")) {all += localStorage.getItem(key) + "\n";}}all += "</body></html>";localStorage.setItem("allEE",all);})();



