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
  CodeMirror.quantitycshHint = function(editor) {
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
"*** basic quantities ***",
"<Acceleration>0.0 m/s^2</Acceleration>",
"<AmountOfSubstance>0.0 mol</AmountOfSubstance>",
"<Angle>0.0 rad</Angle>",
"<AngularAcceleration>0.0 rad/s^2</AngularAcceleration>",
"<AngularMomentum>0.0 kg*m^2/s</AngularMomentum>",
"<AngularVelocity>0.0 rad/s</AngularVelocity>",
"<Area>0.0 m^2</Area>",
"<CatalyticActivity>0.0 kat</CatalyticActivity>",
"<DataAmount>0 bit</DataAmount>",
"<DataRate>0 bit/s</DataRate>",
"<Duration>0.0 s</Duration>",
"<DynamicViscosity>0.0 Pa*s</DynamicViscosity>",
"<ElectricCapacitance>0.0 F</ElectricCapacitance>",
"<ElectricCharge>0.0 C</ElectricCharge>",
"<ElectricConductance>0.0 S</ElectricConductance>",
"<ElectricCurrent>0.0 A</ElectricCurrent>",
"<ElectricInductance>0.0 H</ElectricInductance>",
"<ElectricPotential>0.0 V</ElectricPotential>",
"<ElectricResistance>0.0 Ohm</ElectricResistance>",
"<Energy>0.0 J</Energy>",
"<Force>0.0 N</Force>",
"<Frequency>0.0 Hz</Frequency>",
"<Illuminance>0.0 lx</Illuminance>",
"<KinematicViscosity>0.0 m^2/s</KinematicViscosity>",
"<Length>0.0 m</Length>",
"<LuminousFlux>0.0 lm</LuminousFlux>",
"<LuminousIntensity>0.0 cd</LuminousIntensity>",
"<MagneticFlux>0.0 Wb</MagneticFlux>",
"<MagneticFluxDensity>0.0 T</MagneticFluxDensity>",
"<Mass>0.0 kg</Mass>",
"<MassFlowRate>0.0 kg/s</MassFlowRate>",
"<Momentum>0.0 kg*m/s</Momentum>",
"<Power>0.0 W</Power>",
"<Pressure>0.0 Pa</Pressure>",
"<RadiationDoseAbsorbed>0.0 Gy</RadiationDoseAbsorbed>",
"<RadiationDoseEffective>0.0 Sv</RadiationDoseEffective>",
"<RadioactiveActivity>0.0 Bq</RadioactiveActivity>",
"<SolidAngle>0.0 sr</SolidAngle>",
"<Temperature>0.0 K</Temperature>",
"<Torque>0.0 N*m</Torque>",
"<Velocity>0.0 m/s</Velocity>",
"<Volume>0.0 m^3</Volume>",
"<VolumetricDensity>0.0 kg/m^3</VolumetricDensity>",
"<VolumetricFlowRate>0.0 m^3/s</VolumetricFlowRate>",
"<Work>0.0 N*m</Work>",
"",
"*** basic constants ***",
"<AccelerationDueToGravity/>",
"<ElectronRestMass/>",
"<ProtonRestMass/>",
"<NeutronRestMass/>",
"<DeuteronRestMass/>",
"<MuonRestMass/>",
"<Pi/>",
"<HalfPi/>",
"<TwoPi/>",
"<FourPi/>",
"<PiSquared/>",
"<SpeedOfLight/>",
"<SpeedOfLightSquared/>",
"<BoltzmannConstant/>",
"<PlanckConstant/>",
"<PlanckConstantOver2Pi/>",
"<ElementaryCharge/>",
"<PermeabilityOfVacuum/>",
"<PermittivityOfVacuum/>",
"<ImpedanceOfVacuum/>",
"<FineStructureConstant/>",
"<GravitationalConstant/>",
"<AvogadrosNumber/>",
"<GasConstant/>",
"<FaradayConstant/>",
"<StefanBoltzmannConstant/>",
"<AtomicMassUnit/>",
"<RydbergConstant/>",
"<BohrRadius/>",
"<HartreeEnergy/>",
"<MagneticFluxQuantum/>",
"<ConductanceQuantum/>",
"<BohrMagneton/>",
"<NuclearMagneton/>",
"<PlanckMass/>",
"<PlanckLength/>",
"<PlanckTime/>"
];
    return found;
  }
})();
