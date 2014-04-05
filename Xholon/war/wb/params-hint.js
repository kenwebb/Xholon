/*
  Application Parameters hints
*/
(function () {
  CodeMirror.paramsHint = function(editor) {
    var cur = editor.getCursor();
    var token = editor.getTokenAt(cur);
    token = {start: cur.ch, end: cur.ch, string: "", state: token.state,
                       className: token.string == "." ? "property" : null};
    return {list: getCompletions(),
            from: {line: cur.line, ch: token.start},
            to: {line: cur.line, ch: token.end}};
  }

  function getCompletions() {
    var found = [
'<param name="ShowParams" value="false|true"/>',
'<param name="ModelName" value="Name of the model or app"/>',
'<param name="AppM" value="true|false"/>',
'<param name="InfoM" value="false|true"/>',
'<param name="ErrorM" value="true|false"/>',
'<param name="MaxProcessLoops" value="10"/>',
'<param name="TimeStepInterval" value="10"/>',
'<param name="SizeMessageQueue" value="1"/>',
'<param name="JavaClassName" value="org.primordion.xholon.user.app.mine.Appmine"/>',
'<param name="JavaXhClassName" value="org.primordion.xholon.user.app.mine.Xhmine"/>',
'<param name="MaxPorts" value="1"/>',
'<param name="UseDataPlotter" value="none|google2|gnuplot|c3|nvd3"/>',
'<param name="DataPlotterParams" value="Line Chart Title,Time (timesteps),Y,./statistics/,stats,1,WRITE_AS_LONG"/>',
'<param name="UseInteractions" value="false|true"/>',
'<param name="InteractionParams" value="32,false,localhost,60001"/>',
'<param name="SaveSnapshots" value="false|true"/>',
'<param name="SnapshotParams" value="SnapshotXml|SnapshotYaml|_d3,CirclePack|HTModL|???,.,./snapshot/,false|true"/>'
];
    return found;
  }
})();
