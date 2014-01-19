/**
 * Xholon Workbook
 *
 * Licensed under the MIT license  http://opensource.org/licenses/MIT .
 * Copyright (C) 2013 Ken Webb
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon/gwt/">Xholon Project website</a>
 * @since 0.1 (Created on Jan 15, 2012)
 */
(function () {
  CodeMirror.quantityihHint = function(editor) {
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
'<Albedo superClass="Dimensionless"/>',
'<AngularMomentum superClass="Quantity"/>',
'<AverageSpeed superClass="Velocity"/>',
'<AverageVelocity superClass="Velocity"/>',
'<Breadth superClass="Length"/>',
'<Celsius superClass="Temperature"/>',
'<ChangeInTime superClass="Duration"/>',
'<ChangeInVelocity superClass="Velocity"/>',
'<Density superClass="VolumetricDensity"/>',
'<Depth superClass="Length"/>',
'<Diameter superClass="Length"/>',
'<Displacement superClass="Length"/>',
'<Distance superClass="Length"/>',
'<Fahrenheit superClass="Temperature"/>',
'<FrictionalForce superClass="Force"/>',
'<Heat superClass="Energy"/>',
'<Height superClass="Length"/>',
'<Impulse superClass="Momentum"/>',
'<InstantaneousSpeed superClass="Velocity"/>',
'<InstantaneousVelocity superClass="Velocity"/>',
'<KineticEnergy superClass="Energy"/>',
'<MechanicalEnergy superClass="Energy"/>',
'<Momentum superClass="Quantity"/>',
'<NormalForce superClass="Force"/>',
'<Percentage superClass="Dimensionless"/>',
'<Period superClass="Duration"/>',
'<PotentialEnergy superClass="Energy"/>',
'<RadiantFlux superClass="Power"/>',
'<Radius superClass="Length"/>',
'<Rankine superClass="Temperature"/>',
'<SemimajorAxis superClass="Length"/>',
'<Speed superClass="Velocity"/>',
'<ThermalEnergy superClass="Energy"/>',
'<Thickness superClass="Length"/>',
'<Time superClass="Duration"/>',
'<Weight superClass="Force"/>',
'<Width superClass="Length"/>'
];
    return found;
  }
})();
