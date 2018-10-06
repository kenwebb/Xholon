<script implName="lang:javascript:inline:"><![CDATA[
/*
 * Create a data plotter (chart viewer).
 * It will plot the values of all passive nodes in the Xholon tree.
 * TODO either (1) plot passive nodes, or (2) plot selected nodes
 */
var app = applicationKey;
app.setUseDataPlotter('google'); // or 'JFreeChart'
//app.setDataPlotterParams('Title,Time (timesteps),Y,./statistics/,stats,1,WRITE_AS_LONG');
//app.setDataPlotterParams('Leaky Bucket,Time (s),Depth (m),./statistics/,leakybucket,1,WRITE_AS_DOUBLE');
app.createChart(contextNodeKey);
//app.invokeDataPlotter(); // call this later to display the chart
]]></script>
