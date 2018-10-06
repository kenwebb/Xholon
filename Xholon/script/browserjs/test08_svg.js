<script implName="lang:BrowserJS:inline:"><![CDATA[
      var myText = document.getElementById("myText");
      var myTextFillAttr = myText.getAttribute('fill');
      if (myTextFillAttr == 'red') {
        // use CSS style property, or setAttribute method; these have different effects
        //myText.style.fill = 'green'; // style property
        myText.setAttribute('fill', 'green'); // setAttribute method
        myText.childNodes[0].nodeValue = 'Hola Mundo';
      }
      else if (myTextFillAttr == 'blue') {
        myText.setAttribute('fill', 'red');
        myText.childNodes[0].nodeValue = 'Bonjour Monde';
        // create a circle
        var el = document.createElementNS(svgns, 'circle');
        el.setAttribute('cx', 100);
        el.setAttribute('cy', 100);
        el.setAttribute('r', 20);
        el.setAttribute('fill', 'yellow');
        el.setAttribute('stroke-width', '1px');
        el.setAttribute('stroke', 'black');
        var myGroup = document.getElementById("myGroup");
        myGroup.appendChild(el);
      }
      else {
        myText.setAttribute('fill', 'red');
        myText.childNodes[0].nodeValue = 'Hello World';
      }
nil;
]]></script>
