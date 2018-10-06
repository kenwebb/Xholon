<script implName="lang:BrowserJS:inline:"><![CDATA[
function drawChart() {
  var data = new google.visualization.DataTable();
  data.addColumn('string', 'Year');
  data.addColumn('number', 'Sales');
  data.addColumn('number', 'Expenses');
  data.addRows([
    ['2004', 1000, 400],
    ['2005', 1170, 460],
    ['2006',  860, 580],
    ['2007', 1030, 540]
  ]);

  var options = {
    width: 400, height: 240,
    title: 'Company Performance'
  };

  var chart = new google.visualization.LineChart(document.getElementById('usercontent'));
  chart.draw(data, options);
}
drawChart();
]]></script>