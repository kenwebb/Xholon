<script implName="lang:BrowserJS:inline:"><![CDATA[
$('#divOne').css('background', 'aliceblue');
$('#divOne').css('width', '500px');
$('#divOne').css('height', '300px');
$('#divOne').css('overflow', 'auto');
]]></script>


Examples from Flanagan, JavaScript - The Definitive Guide
http://www.davidflanagan.com/javascript5/
// This code is from the book JavaScript: The Definitive Guide, 5th Edition,
// by David Flanagan. Copyright 2006 O'Reilly Media, Inc. (ISBN #0596101996)

/* 1-1 */
<script implName="lang:BrowserJS:inline:"><![CDATA[
var result = '';
var fact = 1;
for (i = 1; i < 10; i++) {
  fact = fact * i;
  result += '<p>' + i + '! = ' + fact + '</p>';
}
$('div#divFive').html(result);
]]></script>

/* 1-2 */
<script implName="lang:BrowserJS:inline:"><![CDATA[
msg = 'You clicked the button';
$('div#divFive').html("<button onclick='alert(msg);'>Click here</button>");
]]></script>

/* 1-3 content */
<script implName="lang:BrowserJS:inline:"><![CDATA[
$('div#divFive').html("\
<form name='loandata'>\
  <table>\
    <tr><td><b>Enter Loan Information:</b></td></tr>\
    <tr>\
      <td>1) Amount of the loan (any currency):</td>\
      <td><input type='text' name='principal' onchange='calculate();'></td>\
    </tr>\
    <tr>\
      <td>2) Annual percentage rate of interest:</td>\
      <td><input type='text' name='interest' onchange='calculate();'></td>\
    </tr>\
    <tr>\
      <td>3) Repayment period in years:</td>\
      <td><input type='text' name='years' onchange='calculate();'></td>\
    </tr>\
    <tr><td></td>\
      <td><input type='button' value='Compute' onclick='calculate();'></td>\
    </tr>\
    <tr><td><b>Payment Information:</b></td></tr>\
    <tr>\
      <td>4) Your monthly payment:</td>\
      <td>$<span class='result' id='payment'></span></td>\
    </tr>\
    <tr>\
      <td>5) Your total payment:</td>\
      <td>$<span class='result' id='total'></span></td>\
    </tr>\
    <tr>\
      <td>6) Your total interest payments:</td>\
      <td>$<span class='result' id='totalinterest'></span></td>\
    </tr>\
  </table>\
</form>\
");
]]></script>

/* 1-3 presentation */
<script implName="lang:BrowserJS:inline:"><![CDATA[
$('.result').css('font-weight', 'bold');
$('#payment').css('text-decoration', 'underline');
]]></script>

/* 1-3 behavior */
<script implName="lang:BrowserJS:inline:"><![CDATA[
$('div#divHeadOne').html("<script language='JavaScript'>\
function calculate() {\
    var principal = document.loandata.principal.value;\
    var interest = document.loandata.interest.value / 100 / 12;\
    var payments = document.loandata.years.value * 12;\
    var x = Math.pow(1 + interest, payments);\
    var monthly = (principal*x*interest)/(x-1);\
    var payment = document.getElementById('payment');\
    var total = document.getElementById('total');\
    var totalinterest = document.getElementById('totalinterest');\
    if (isFinite(monthly)) {\
        payment.innerHTML = monthly.toFixed(2);\
        total.innerHTML = (monthly * payments).toFixed(2);\
        totalinterest.innerHTML = ((monthly*payments)-principal).toFixed(2);\
    }\
    else {\
        payment.innerHTML = '';\
        total.innerHTML = '';\
        totalinterest.innerHTML = '';\
    }\
}\
</script>");
]]></script>

/* p. 128 */
<script implName="lang:BrowserJS:inline:"><![CDATA[
function copyPropertyNamesToArray(o, /* optional */ a) {
  if (!a) {a = []};
  for (var property in o) {
    a.push(property);
  }
  return a;
}
var a = copyPropertyNamesToArray($('#divOne'));
alert(a);
var a = copyPropertyNamesToArray($('#divOne').get());
alert(a);
]]></script>


Examples from jQuery site:
http://jquery.com/

<script implName="lang:BrowserJS:inline:"><![CDATA[
$('div#divFive').html(function() {
  var emph = '<em>' + $('p').length + ' paragraphs!</em>';
  return '<p>All new content for ' + emph + '</p>';
});
]]></script>


