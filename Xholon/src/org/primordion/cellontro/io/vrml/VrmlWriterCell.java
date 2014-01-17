/* Cellontro - models & simulates cells and other biological entities
 * Copyright (C) 2005, 2006, 2007, 2008 Ken Webb
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA
 */

package org.primordion.cellontro.io.vrml;

//import java.io.IOException;
//import java.io.Writer;
import java.util.Date;
import java.util.Random;
import java.util.Vector;

import org.primordion.cellontro.app.CeLife;
import org.primordion.cellontro.app.XhLife;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.io.XholonGwtTabPanelHelper;
import org.primordion.xholon.io.vrml.AbstractVrmlWriter;
import org.primordion.xholon.io.vrml.IVrmlWriter;
import org.primordion.xholon.io.vrml.RGBTypeDouble;
import org.primordion.xholon.io.vrml.Rotations;
import org.primordion.xholon.io.vrml.SFRotation;
import org.primordion.xholon.io.vrml.SFVec3f;
import org.primordion.xholon.io.vrml.VrmlBuffer;
//import org.primordion.xholon.util.MiscIo;

/**
 * VRML Writer. Writes a file in VRML 97 (VRML 2) format.
 * Each Xholon in the current application is written out as a VRML node.
 * The file can subsequently be viewed in 3D using a VRML tool,
 * such as Cortona from Parallel Graphics.
 * To view it completely, a number of other VRML (.wrl)
 * and PNG (.png) files are required.
 * 
 * GWT 2013 version (Dec 23 2013)
 * The output is intended as input to threejs VRMLLoader.js .
 * It expects everything to be on its own line,
 * so there must be a \n after all { and [
 * and } and ] must be alone on their own line.
 * It doesn't handle text.
 * I don't know yet if it handles textures defined in external files, such as  url "cellBilayer.png"
 * I don't think it handles EXTERNPROTO
 * The threejs camera will need to be manually set, to accomodate the large sizes of everything:
 *   camera.position.x = 0;
 *   camera.position.y = 2500;
 *   camera.position.z = 62500;
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on Oct 26, 2005)
 */
public class VrmlWriterCell extends AbstractVrmlWriter implements IVrmlWriter, CeLife {

	private static final int Size_pHScale = 15; // 0 to 14
	private static final int MaxEnzymes = 25; // max # of enzymes in Cytoplasm
	// whether to create various .wrl files, such as RoughEr.wrl and SmoothEr.wrl
	// true: creates these files
	// false: assumes that these files have been previously created
	private static boolean roughErAlreadyWritten = false;
	private static boolean smoothErAlreadyWritten = false;

	// Small molecules tend to range in size from 0.4 to 0.9 nanometers (nm)
	// (source: Becker, p.6)
	// I am using an average value of 0.5 nm, which means there are 2 SM per nm
	// Number of small molecules per nm
	private static final double NumSmPerNm        = 2.0;
	// Number of small molecules per square nm
	private static final double NumSmPerNmSquared = NumSmPerNm * NumSmPerNm;
	// Number of small molecules per cubic nm (not currently used)
	//private static final double NumSmPerNmCubed   = NumSmPerNm * NumSmPerNm * NumSmPerNm;
	
	// Enzyme ring initialization flag
	static boolean isEnzymeRingInitialized = false;

	private double nucleusVolume;
	private VrmlColorCell vColor;
	private RGBTypeDouble pHcolorArray[]; // colors of pH in a solution
	private SFVec3f enzymeRing[]; // x,y,z coordinates for enzymes
	private int enzymeIx; // index into enzymeRing
	private RGBTypeDouble enzymeColorArray[];
	private CellLocations cellLocations;
	private CytoplasmLocations cytoLocations;

	/**
	 * Constructor.
	 */
	public VrmlWriterCell() {
		int i;
		v = new VrmlBuffer();
		rot = new Rotations();
		// set volume based on current static size of nucleus
		setNucleusVolume( 12500.0 / 3.0 );
		vColor = new VrmlColorCell();
		pHcolorArray = new RGBTypeDouble[Size_pHScale];
		for (i = 0; i < Size_pHScale; i++) {
			pHcolorArray[i] = new RGBTypeDouble();
		}
		vColor.fillColorArray( pHcolorArray, Size_pHScale, VrmlColorCell.SvTypepHIndicator );
		enzymeRing = new SFVec3f[MaxEnzymes];
		for (i = 0; i < MaxEnzymes; i++) {
			enzymeRing[i] = new SFVec3f();
		}
		enzymeIx = 0;
		enzymeColorArray = new RGBTypeDouble[MaxEnzymes];
		for (i = 0; i < MaxEnzymes; i++) {
			enzymeColorArray[i] = new RGBTypeDouble();
		}
		cellLocations = new CellLocations();
		cytoLocations = new CytoplasmLocations();
		random = new Random();
	}
	
	/**
	 * Write VRML .wrl file
	 * @param xhNode Xholon node from which to start outputting the VRML.
	 */
	protected void writeWrlFile(IXholon xhNode) {
		//Writer out = MiscIo.openOutputFile(vrmlWriteDir + "rootCell.wrl");
		//if (out == null) {return;}
		StringBuilder sbOut = new StringBuilder();
		//try {
			sbOut.append("#VRML V2.0 utf8\n")
			.append("#Generated using Xholon Cellontro\n")
			.append("#" + new Date() + "\n")
			.append("# To view this VRML, use threejs.org (WebGL, canvas) with its VRMLLoader.js example\n")
			.append("\n")
			.append("#DEF NavInfoA NavigationInfo { speed 10000 }\n" )
			.append("#DEF CameraA Viewpoint { position 0 0 80000 description \"Cell\" }\n" )
			.append("#DEF CameraB Viewpoint { position 0 100000 2500000 description \"CellDistant\" }\n" )
			.append("#DEF CameraC Viewpoint { position 0 1580000000 250000000 description \"NervousSystem\" }\n" )
			.append("#DEF CameraD Viewpoint { position 0 1850000000 0 description \"Brain\" }\n" )
			.append("#EXTERNPROTO MitochondrialOuterBilayer [ ] \"MitochondrialOuterBilayer.wrl#MitochondrialOuterBilayer\"\n" )
			.append("#EXTERNPROTO RoughEr [ ] \"RoughEr.wrl#RoughEr\"\n" )
			.append("#EXTERNPROTO SmoothEr [ ] \"SmoothEr.wrl#SmoothEr\"\n" )
			.append("#EXTERNPROTO GolgiComplex [ ] \"GolgiComplex.wrl#GolgiComplex\"\n" )
			// tell Cortona screen saver to auto rotate worlds; other browsers will ignore
			.append("#WorldInfo { info [  \"cortsaver:autorotate   8   0 0.8 -0.6\" ] }\n" )
			.append("\n")
			.append("DEF ROOT Group {\n" )
			.append("children [\n" );
			showStructure( sbOut, xhNode );
			sbOut.append("]\n}\n" );
			//MiscIo.closeOutputFile( out );
		//} catch (IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
    XholonGwtTabPanelHelper.addTab(sbOut.toString(), "vrml", vrmlWriteDir + "rootCell.wrl", false);
	}
	
	/**
	 * Describe an individual Xholon.
	 * @param sbOut Output StringBuilder.
	 * @param xhNode Xholon node.
	 */
	protected void describeXholon(StringBuilder sbOut, IXholon xhNode) {
		boolean detailWritten = false;
		v.init();
		v.writeDefNodeName( xhNode.getName() );

		SFVec3f myLocation = null;
		double myRadius = 0.0;
		
		switch( xhNode.getXhcId() ) { // a long switch
		
		case ACarbonCE:
			break;

		case AminoAcidCE:
		case AlaCE:
		case ArgCE:
		case AsnCE:
		case AspCE:
		case CysCE:
		case GlnCE:
		case GluCE:
		case GlyCE:
		case HisCE:
		case IleCE:
		case LeuCE:
		case LysCE:
		case MetCE:
		case PheCE:
		case ProCE:
		case SerCE:
		case ThrCE:
		case TrpCE:
		case TyrCE:
		case ValCE:
			break;

		case AminoAcidPaletteCE:
			break;
		case AminoGroupCE:
			break;
		case AnchorProteinCE:
			break;

		case AnimalCE:
		case HumanBeingCE:
			break;

		case ArchebacteriaCE:
			break;
		case AstrocyteCE:
			break;

		case AstrocyteFootCE:
		case AstrocyteFootCapillaryCE:
		case AstrocyteFootNeuronCE:
			break;

		case AstrocyteWithInnardsCE:
			break;
		case AtmosphereCE:
			break;

		case AtriumCE:
		case LeftAtriumCE:
		case RightAtriumCE:
			break;

		case AxonCE:
			break;
		case AxonHillockCE:
			break;

		case AxonSegmentCE:
		case AxonSegmentSplitterCE:
			break;

		case BaseCE:
		case PurineCE:
		case AdenineCE:
		case GuanineCE:
		case PyrimidineCE:
		case CytosineCE:
		case ThymineCE:
		case UracilCE:
			break;

		case BaseSugarCE:
		case DeoxyriboseCE:
		case RiboseCE:
			break;

		case BloodPlasmaCE:
			break;

		case BloodVesselCE:
		case ArteryCE:
		case SpinalArteryCE:
		case CapillaryCE:
		case VeinCE:
		case SpinalVeinCE:
			break;

		case BodyOfWaterCE:
			break;
		case BoneMarrowCE:
			break;
		case BovineRibonucleaseCE:
			break;
		case BovineRibonucleaseMoleculeCE:
			break;
		case BovineRibonucleaseWithAaActorsCE:
			break;
		case CarboxylGroupCE:
			break;

		case CelestialBodyCE:
		case EarthCE:
		case SunCE:
			break;

		case CellWallCE:
			break;
		case ChloroplastCE:
			break;
		case ChloroplastDualMembraneCE:
			break;
		case ChloroplastIntermembraneSpaceCE:
			break;
		case CholineCE:
			break;
		case ChromosomeCE:
			v.write( "children [\n" );
			v.write( "Shape {\n");
			v.write( "geometry Sphere {\nradius ");
			v.write( cellLocations.getRadius() / 4.0 ); // get radius
			v.write( "\n}\n");
			v.write( "appearance Appearance {\n");
			v.write( "material Material {\n}\ntexture ImageTexture {\nurl \"chromatin.png\"\n}\n");
			v.write( "}\n}\n");
			detailWritten = true;
			break;
		case CirculatorySystemCE:
			break;
		case CisternaeCE:
			break;
		case ColonCE:
			break;
		case ConfigurationAgentCE:
			break;
		case CytoSkeletonCE:
			break;
		case CytoplasmCE:
			if (doClassicStyle == false) {
				resetEnzymeRing( 5000.0, 90.0 );
			}
			break;
		case DendriteCE:
			break;
		case DendritesCE:
			break;
		case DigestedFoodCE:
			break;
		case DigestiveSystemCE:
			break;

		case DnaCE:
		case DnaTest1CE:
			break;

		// EndoplasmicReticulum
		case EndoplasmicReticulumCE:
			break;
		case RoughErCE:
			v.write( "translation " );
			v.write( 0.0, 0.0, 0.0 );
			v.write( "\nchildren [\n" );
			if (createProtoFiles) {
				writeRoughEr();
			}
			v.write( "RoughEr {\n}\n" );
			detailWritten = true;
			break;
		case SmoothErCE:
			v.write( "translation " );
			v.write( 0.0, 0.0, 0.0 );
			v.write( "\nchildren [\n" );
			if (createProtoFiles) {
				writeSmoothEr();
			}
			v.write( "SmoothEr {\n}\n" );
			detailWritten = true;
			break;

		case EndosomeCE:
			break;

		case EnzymeCE: // Enzymes
		case SucraseCE:
		case AcetylcholinesteraseCE:
		case GlycolysisEnzymeCE: // Glycolysis Enzymes
		case HexokinaseCE:
		case PhosphoGlucoIsomeraseCE:
		case PhosphoFructokinaseCE:
		case AldolaseCE:
		case TriosePhosphateIsomeraseCE:
		case Glyceraldehyde_3_phosphateDehydrogenaseCE:
		case PhosphoGlycerokinaseCE:
		case PhosphoGlyceromutaseCE:
		case EnolaseCE:
		case PyruvateKinaseCE:
		case PyruvateDecarboxylaseCE:
		case LactateDehydrogenaseCE:
		case AlcoholDehydrogenaseCE:
		case PyruvateCarboxylaseCE:
		case PhosphoenolPyruvateCarboxykinaseCE:
		case Fructose_1x6_bisphosphataseCE:
		case Glucose_6_phosphataseCE:
		case TcaEnzymeCE: // TCA Enzymes
		case PyruvateDehydrogenaseCE:
		case CitrateSynthaseCE:
		case AconitaseCE:
		case IsocitrateDehydrogenaseCE:
		case A_KetoglutarateDehydrogenaseCE:
		case SuccinylCoASynthetaseCE:
		case SuccinateDehydrogenaseCE:
		case FumarateHydrataseCE:
		case MalateDehydrogenaseCE:
			if (!doClassicStyle) {
				writeEnzyme( xhNode );
				detailWritten = true;
			}
			break;

		case Page320KinaseCE:
		case Page320PhosphataseCE:
		case Pfk_2CE:
			break;

		case EsophagusCE:
			break;
		case EubacteriaCE:
			break;

		case EukaryoteCE:
			break;

		// EukaryoticCell
		case EukaryoticCellCE:
		case AstrocyteCellBodyCE:
		case ErythrocyteCE:
		case MucosalCellCE:
		case MuscleCellBodyCE:
		case NeuronCellBodyCE:
			if (xhNode.getXhcId() == EukaryoticCellCE) {
				myLocation = cellLocations.getXYZ();
				myRadius = cellLocations.getRadius() * 1.1;
			}
			else if (xhNode.getXhcId() == ErythrocyteCE) {
				myLocation = new SFVec3f();
				myLocation.x = random.nextDouble() * 10.0;
				myLocation.y = random.nextDouble() * 10.0;
				myLocation.z = random.nextDouble() * 10.0;
			}
			else if (xhNode.getXhcId() == MucosalCellCE) {
				myLocation = new SFVec3f();
				myLocation.x = random.nextDouble() * -10.0;
				myLocation.y = random.nextDouble() * 10.0;
				myLocation.z = random.nextDouble() * 10.0;
			}
			else if (xhNode.getXhcId() == NeuronCellBodyCE) {
				myLocation = new SFVec3f();
				myLocation.x = random.nextDouble() * -10.0;
				myLocation.y = random.nextDouble() * -10.0;
				myLocation.z = random.nextDouble() * 10.0;
			}
			v.write( "translation " );
			v.write( myLocation.x, myLocation.y, myLocation.z );
			writeRandomRotation();
			v.write( "children [\n" );
			if (useLOD) {
			  v.write( "LOD {\n" );
			  v.write( "range [\n50000, 500000, 5000000\n]\n" );
			  v.write( "level [\n" );
			  v.write( "# Level 0\n" );			// Level 0
			  v.write( "WorldInfo {\n}\n" );
			  v.write( "# Level 1\n" );			// Level 1
			  v.write( "Shape {\n");
			  v.write( "geometry Sphere {\nradius ");
			  v.write( myRadius );
			  v.write( "}\n");
			  v.write( "appearance Appearance {\n");
			  v.write( "material Material {\n}\ntexture ImageTexture {\nurl \"EukaryoticCell.png\"\n}\n");
			  v.write( "}\n}\n");
			  v.write( "# Level 2\n" );			// Level 2
			  v.write( "Shape {\n");
			  v.write( "geometry Sphere {\nradius ");
			  v.write( myRadius );
			  v.write( "}\n");
			  v.write( "appearance Appearance {\n");
			  v.write( "material Material {\ndiffuseColor ");
			  v.write( 0.5, 0.5, 0.5 );
			  v.write( " transparency 0.0\n}\n");
			  v.write( "}\n}\n");
			  v.write( "# Level 3\n" );			// Level 3
			  v.write( "WorldInfo {\n}\n" );
			  v.write( "]\n}\n");
			}
			detailWritten = true;
			break;

		case EyeCE:
			break;
		case FibrilCE:
			break;

		case FilamentCE:
		case IntermediateFilamentCE:
		case MicroFilamentCE:
			break;

		case FoodCE:
			break;

		// Gaia
		case GaiaCE:
		case SimpleEcologyCE:
			break;
		case SingleCellCE:
			break;

		case GlucagonCE:
			break;
		case GlycerolCE:
			break;
		case GlycolipidCE:
			break;

		case GolgiComplexCE:
			v.write( "translation " );
			myLocation = cytoLocations.getOr();
			v.write( myLocation.x, myLocation.y, myLocation.z );
			v.write( "\nchildren [\n" );
			v.write( "GolgiComplex {\n}\n" );
			detailWritten = true;
			break;

		case GolgiSacculeCE:
			break;
		case GranuleCE:
			break;
		case GranumCE:
			break;
		case HeartCE:
			break;
		case HeatCE:
			break;
		case HemeCE:
			break;
		case HemoglobinCE:
			break;
		case HemoglobinMoleculeCE:
			break;
		case HydrogenAtomCE:
			break;
		case InorganicPrecursorCE:
			break;

		case InternalSpaceCE:
		case CisternalSpaceCE:
		case EndosomeSpaceCE:
		case GolgiSpaceCE:
		case LysosomeSpaceCE:
		case PerinuclearSpaceCE:
		case PeroxisomeSpaceCE:
		case SecretoryVesicleSpaceCE:
		case SynapticVesicleSpaceCE:
		case ExtraCellularSpaceCE:
			break;

		case LandCE:
			break;
		case LargeIntestineCE:
			break;

		case LgNeuroreceptorCE:
		case AmpaKainateReceptorCE:
		case GabaAReceptorCE:
		case NicotinicAChReceptorCE:
		case NmdaReceptorCE:
			break;

		case LifeTheUniverseAndEverythingCE:
			break;
		case LipidCE:
			break;

		// LipidBilayer
		case LipidBilayerCE:
			break;
		case AxonBilayerCE:
			break;
		case CellBilayerCE:
			// There are 2 views here; TODO should be within a LOD node
			// NO. LOD is in EukaryoticCell
			if (doClassicStyle) {
				v.write( "children [\n" );
				v.write( "Shape {\n");
				v.write( "geometry Sphere {\nradius ");
				v.write( cellLocations.getRadius() );
				v.write( "\n}\n");
				v.write( "appearance Appearance {\n");
				// v.write( "material Material { diffuseColor ");
				// v.write( 0.5, 0.5, 0.5 );
				// v.write( " transparency 0.6 } ");
				v.write( "material Material {\n}\ntexture ImageTexture {\nurl \"cellBilayer.png\"\n}\n");
				v.write( "}\n}\n");
			}
			else {
				writeLipids( xhNode );
			}
			detailWritten = true;
			break;
		case ChloroplastBilayerCE:
			break;
		case DendriteBilayerCE:
			break;
		case EndosomeBilayerCE:
			break;
		case ErBilayerCE:
			break;
		case GolgiBilayerCE:
			break;
		case LysosomeBilayerCE:
			break;
		case MitochondrialInnerBilayerCE:
			break;
		case MitochondrialOuterBilayerCE:
			v.write( "children [\n" );
			v.write( "MitochondrialOuterBilayer {\n}\n" );
			detailWritten = true;
			break;
		case NuclearInnerBilayerCE:
			break;
		case NuclearOuterBilayerCE:
			setNucleusVolume( xhNode );	// needed later
			v.write( "children [\n" );
			v.write( "Shape {\n");
			v.write( "geometry Sphere {\nradius ");
			v.write( cellLocations.getRadius() / 3.0 );
			v.write( "\n}\n");
			v.write( "appearance Appearance {\n");
			v.write( "material Material {\n}\ntexture ImageTexture {\nurl \"nucleus.png\"\n}\n");
			v.write( "}\n}\n");
			detailWritten = true;
			break;
		case PeroxisomeBilayerCE:
			break;
		case SecretoryVesicleBilayerCE:
			break;
		case SynapticVesicleBilayerCE:
			break;
		case TerminalButtonBilayerCE:
			break;
		case VacuoleBilayerCE:
			break;

		case LipidLayerCE:
			break;
		case LipidNonPolarTailCE:
			break;
		case LipidPolarHeadCE:
			break;
		case LungCE:
			break;
		case LysosomeCE:
			break;
		case MatrixCE:
			break;

		// Membrane
		case MembraneCE:
		case AxonMembraneCE:
		case CellMembraneCE:
		case MucosalCellMembraneCE:
		case ChloroplastMembraneCE:
		case DendriteMembraneCE:
		case EndosomeMembraneCE:
		case ErMembraneCE:
		case GolgiMembraneCE:
		case LysosomeMembraneCE:
		case MitochondrialInnerMembraneCE:
		case MitochondrialOuterMembraneCE:
		case NuclearInnerMembraneCE:
		case NuclearOuterMembraneCE:
		case PeroxisomeMembraneCE:
		case SecretoryVesicleMembraneCE:
		case SynapticVesicleMembraneCE:
		case TerminalButtonMembraneCE:
		case VacuoleMembraneCE:
			break;

		case MicroTubuleCE:
			break;
		case MitochondrialDualMembraneCE:
			break;
		case MitochondrialIntermembraneSpaceCE:
			break;

		case MitochondrionCE:
			v.write( "translation " );
			myLocation = cytoLocations.getOr();
			v.write( myLocation.x, myLocation.y, myLocation.z );
			writeRandomRotation();
			v.write( "children [\n" );
			detailWritten = true;
			break;

		case MotorEndPlateCE:
			break;
		case MouthCE:
			break;

		case MRnaCE:
		case MRnaTest1CE:
			break;

		case MuscleCE:
			break;
		case MuscleFiberCE:
			break;

		case MyelinCellCE:
		case OligodendrocyteCE:
		case SchwannCellCE:
			break;

		case NervousSystemCE:
			break;

		// NervousSystemEntity
		case NervousSystemEntityCE:
		case BrainCE:
		case CeliacGanglionCE:
		case CerebellumCE:
		case CentralNervousSystemCE:
		case CerebralCortexCE:
		case CerebrumCE:
		case DiencephalonCE:
		case ForeBrainCE:
		case HindBrainCE:
		case HypoThalamusCE:
		case InferiorMesentericGanglionCE:
		case MedullaCE:
		case MidBrainCE:
		case ParasymOrganGanglionCE:
		case PeripheralNervousSystemCE:
		case PonsCE:
		case SympatheticChainCE:
		case SympatheticChainGanglionCE:
		case ThalamusCE:
			break;
		case DorsalRootGanglionCE:
		case LateralGeniculateNucleusCE:
		case MotorCortexCE:
		case PrimaryVisualCortexCE:
		case RetinaCE:
		case RetinaConesAndRodsCE:
			v.write( "translation " );
			//v.write( p->x0, p->y0, p->z0 );
			v.write( 0.0, 0.0, 0.0); // not sure what values go here; will probably never be used
			v.write( "\nchildren [\n" );
			v.write( "Shape {\n");
			v.write( "geometry Box {\nsize 1000000.0 1000000.0 1000000.0\n}\n");
			v.write( "appearance Appearance {\n");
			v.write( "material Material {\ndiffuseColor ");
			if (xhNode.getXhcId() == DorsalRootGanglionCE)
				v.write( 1.0, 1.0, 0.0 ); // yellow
			else if (xhNode.getXhcId() == LateralGeniculateNucleusCE)
				v.write( 1.0, 0.0, 0.0 ); // red
			else if (xhNode.getXhcId() == MotorCortexCE)
				v.write( 0.0, 1.0, 0.0 ); // green
			else if (xhNode.getXhcId() == PrimaryVisualCortexCE)
				v.write( 0.0, 0.0, 1.0 ); // blue
			else if (xhNode.getXhcId() == RetinaCE)
				v.write( 1.0, 0.0, 1.0 ); // purple
			else // RetinaConesAndRodsCE
				v.write( 1.0, 1.0, 0.0 ); // yellow
			v.write( " transparency 0.6\n}\n");
			v.write( "}\n}\n");
			detailWritten = true;
			break;
		case SpinalCordCE:
		case SpinalCordRegionCE:
		case SpinalCordCervicalRegionCE:
		case SpinalCordCoccygealRegionCE:
		case SpinalCordLumbarRegionCE:
		case SpinalCordSacralRegionCE:
		case SpinalCordThoracicRegionCE:
			break;
		case DorsalHornLeftCE:
		case DorsalHornRightCE:
		case VentralHornLeftCE:
		case VentralHornParasymCE:
		case VentralHornRightCE:
		case VentralHornSymCE:
			v.write( "translation " );
			//v.write( p->x0, p->y0, p->z0 );
			v.write( "\nchildren [\n" );
			v.write( "Shape {\n");
			v.write( "geometry Box {\nsize 1000000.0 19000000.0 1000000.0\n}\n");
			v.write( "appearance Appearance {\n");
			v.write( "material Material {\ndiffuseColor ");

			switch(xhNode.getParentNode().getXhcId()) {
			case SpinalCordCervicalRegionCE:
				v.write( 1.0, 1.0, 0.0 ); // yellow
				break;
			case SpinalCordThoracicRegionCE:
				v.write( 1.0, 0.0, 1.0 ); // purple
				break;
			case SpinalCordLumbarRegionCE:
				v.write( 0.0, 1.0, 0.0 ); // green
				break;
			case SpinalCordSacralRegionCE:
				v.write( 1.0, 0.0, 0.0 ); // red
				break;
			case SpinalCordCoccygealRegionCE:
				v.write( 0.0, 0.0, 1.0 ); // blue
				break;
			default:
				break;
			}

			v.write( " transparency 0.6\n}\n");
			v.write( "}\n}\n");
			detailWritten = true;
			break;

		case NervousSystemModelCE:
			break;

		// Neuron
		case NeuronCE:
		case CentralNeuronCE:
		case AfferentCentralNeuronCE:
		case BipolarCellCE:
		case GanglionCellCE:
		case EfferentCentralNeuronCE:
		case InterNeuronCE:
		case SpinalInterNeuronCE:
		case MotorNeuronCE:
		case SensoryReceptorNeuronCE:
		case FreeNerveEndingNeuronCE:
		case MechanoReceptorNeuronCE:
		case MeissnerCorpuscleNeuronCE:
		case MerkelsDiskNeuronCE:
		case MuscleSpindleNeuronCE:
		case PacinianCorpuscleNeuronCE:
		case RuffinisCorpuscleNeuronCE:
		case PhotoReceptorNeuronCE:
		case ConeCE:
		case RodCE:
		case SkeletalMuscleCellCE:
			v.write( "translation " );
			//v.write( p->x0, p->y0, p->z0 );
			v.write( "\nchildren [\n" );
			detailWritten = true;
			break;

		case NeuronCapillaryConnectorCE:
			break;
		case NeuronCapillaryConnectorsCE:
			break;
		case NeuronSystem1CE:
			break;
		case NuclearEnvelopeCE:
			break;
		case NuclearLaminaCE:
			break;
		case NuclearPoreCE:
			break;
		case NuclearPoreAqueousChannelCE:
			break;
		case NucleoidCE:
			break;
		case NucleolusCE:
			v.write( "children [\n" );
			v.write( "Shape {\n");
			v.write( "geometry Sphere {\nradius ");
			v.write( cellLocations.getRadius() / 6.0 );
			v.write( "\n}\n");
			v.write( "appearance Appearance {\n");
			v.write( "material Material {\ndiffuseColor ");
			v.write( 1.0, 0.0, 0.0 );
			v.write( "\n}\n");
			v.write( "}\n}\n");
			detailWritten = true;
			break;
		case NucleoplasmCE:
			break;

		case NucleotideCE:
		case DnaNucleotideCE:
		case DAmpCE:
		case DCmpCE:
		case DGmpCE:
		case DTmpCE:
		case RnaNucleotideCE:
		case AmpCE:
		case CmpCE:
		case GmpCE:
		case UmpCE:
			break;

		case NucleusCE:
			break;
		case OrganicMatterCE:
			break;
		case PalmitateCE:
			break;
		case PeroxisomeCE:
			break;
		case PhosphateGroupCE:
			break;

		case PhospholipidCE:
		case variousPhospholipidSubclassesCE:
			break;

		case PlantCE:
		case LettuceCE:
			break;

		case PolyPeptideCE:
		case BovineRibonucleasePPCE:
		case HemoglobinPPaCE:
		case HemoglobinPPbCE:
			break;

		case PrionCE:
			break;
		case ProkaryoteCE:
			break;
		case ProkaryoticCellCE:
			break;

		case RGroupCE:
		case RgAlaCE:
		case RgArgCE:
		case RgAsnCE:
		case RgAspCE:
		case RgCysCE:
		case RgGlnCE:
		case RgGluCE:
		case RgGlyCE:
		case RgHisCE:
		case RgIleCE:
		case RgLeuCE:
		case RgLysCE:
		case RgMetCE:
		case RgPheCE:
		case RgProCE:
		case RgSerCE:
		case RgThrCE:
		case RgTrpCE:
		case RgTyrCE:
		case RgValCE:
			break;

		case RRnaCE:
			break;
		case RectumCE:
			break;
		case RegulatoryComplexCE:
			break;
		case RibosomeCE:
			break;
		case RingSubunitCE:
			break;

		case RnaPolymeraseCE:
		case RnaPolymeraseICE:
		case RnaPolymeraseIICE:
		case RnaPolymeraseIIICE:
			break;

		case RockCE:
			break;

		case SensorCE:
		case FreeNerveEndingCE:
		case MechanoReceptorCE:
		case MeissnerCorpuscleCE:
		case MerkelsDiskCE:
		case MuscleSpindleCE:
		case PacinianCorpuscleCE:
		case RuffinisCorpuscleCE:
		case PhotoReceptorCE:
		case ConeOuterSegmentCE:
		case RodOuterSegmentCE:
			break;

		case SmallIntestineCE:
			break;

		case SoilCE:
			break;
		case SolarSystemCE:
			break;

		// Solution
		case SolutionCE:
			break;
		case CellularSolutionCE:
			break;
		case ChloroplastFluidCE:
			break;
		case CytosolCE:
			if (doClassicStyle) {
				v.write( "children [\n" );
				v.write( "Shape {\n");
				v.write( "geometry Sphere {\nradius ");
				v.write( cellLocations.getRadius() * 0.95 );
				v.write( "\n}\n");
				v.write( "appearance Appearance {\n");
				v.write( "material Material {\ndiffuseColor ");
				v.write( 1.0, 1.0, 1.0 );
				v.write( " transparency 0.5\n}\n");
				v.write( "}\n}\n");
			}
			else {
				writeSolution( xhNode, 12000.0 );
				writeSmallMolecules( xhNode, 12000.0 );
			}
			
			detailWritten = true;
			break;
		case EndosomeFluidCE:
			break;
		case ErFluidCE:
			break;
		case ExtraCellularSolutionCE:
			break;
		case GolgiFluidCE:
			break;
		case LysosomeFluidCE:
			break;
		case MatrixsolCE:
			break;
		case MitochondrialIntermembranesolCE:
			break;
		case NucleosolCE:
			break;
		case PerinuclearFluidCE:
			break;
		case PeroxisomeFluidCE:
			break;
		case SecretoryVesicleFluidCE:
			break;
		case SynapticCleftFluidCE:
			break;
		case SynapticVesicleFluidCE:
			break;
		case TerminalButtonFluidCE:
			break;
		case GaseousSolutionCE:
			break;

		case SphingolipidCE:
			break;
		case SpokeCE:
			break;

		case SteroidCE:
		case CholesterolCE:
			break;

		case SteroidHormoneCE:
		case AndrogenCE:
		case TestosteroneCE:
		case EstrogenCE:
		case EstradiolCE:
		case ProgesteroneCE:
			break;

		case StomachCE:
			break;
		case StromaLamellaeCE:
			break;
		case SynapsesCE:
			break;
		case SynapticCleftCE:
			break;
		case TRnaCE:
			break;
		case TerminalButtonCE:
			break;
		case TerminalButtonPlasmCE:
			break;
		case TerminalButtonsCE:
			break;
		case TheArrowOfTimeCE:
			break;
		case ThylakoidCE:
			break;

		case TranscriptionComplexCE:
		case TranscriptionComplexICE:
		case TranscriptionComplexIICE:
		case TranscriptionComplexIIICE:
			break;

		case TranscriptionFactorBCE:
		case TfIIDCE:
			break;

		case TranscriptionFactorBPassiveCE:
		case TfIIBCE:
		case TfIIECE:
		case TfIIFCE:
		case TfIIHCE:
			break;

		case TranscriptionFactorRCE:
		case CtfCE:
		case Sp1CE:
			break;

		case TransportProteinCE:
		case PyruvateTransporterCE:
		case SucroseTransporterCE:
			break;

		case TransporterCE:
			break;
		case TriglycerideCE:
			break;
		case VacuoleCE:
			break;

		case VentricleCE:
		case LeftVentricleCE:
		case RightVentricleCE:
			break;

		case VesFusionProteinCE:
		case SynaptotagminCE:
			break;

		case VesicleCE:
		case SecretoryVesicleCE:
		case SynapticVesicleCE:
			break;

		case ViroidCE:
			break;
		case VirusCE:
			break;

		case VoltageGatedChannelCE:
		case VoltageGatedCaChannelCE:
		case VoltageGatedKChannelCE:
		case VoltageGatedNaChannelCE:
			break;

		case SolventCE:
		case LipidDisintegrationCE:
			break;

		case WaterCE:
			if (!doClassicStyle) {
				writeWater(xhNode);
				detailWritten = true;
			}
			break;
			
		default:
			Xholon.getLogger().error("VrmlWriter: node not handled = " + xhNode.getXhcId() + " " + xhNode.getName());
			//System.out.println( "VrmlWriter: node not handled = " + xhNode.getXhcId() + " " + xhNode.getName() );
			//if ( (p->x0 != 0) || (p->y0 != 0) || (p->z0 != 0) ) {
			//	v.write( "translation " );
			//	v.write( p->x0, p->y0, p->z0 );
			//	v.write( "\nchildren [\n" );
			//}
			//else
				v.write( "children [\n" );
				detailWritten = true;
			break;
		} // end switch

		if (!detailWritten)
			v.write( "children [\n" );

		//try {
		//	out.write( v.get() );
		//} catch (IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
		
		sbOut.append( v.get() );
		
	} // end describeXholon
	
	/**
	 * Set the volume of the nucleus.
	 * @param radius Radius of the nucleus in nm.
	 */
	protected void setNucleusVolume( double radius ) {
		nucleusVolume = (4.0 / 3.0) * Math.PI * Math.pow(radius, 3.0);
	}
	/**
	 * Set the volume of the nucleus.
	 * TODO make it do something
	 * @param xhNode 
	 */
	protected void setNucleusVolume( IXholon xhNode ) {
		// setNucleusVolume( (double) (p->r) );
	}
	/**
	 * Get the volume of the nucleus.
	 * @return Nucleus volume in nm.
	 */
	protected double getNucleusVolume() {
		return nucleusVolume;
	}

	/**
	 * Write Rough Endoplasmic Reticulum (ER) Proto file.
	 */
	protected void writeRoughEr() {
		if (roughErAlreadyWritten) {
			return;
		}
		roughErAlreadyWritten = true;

		double radius = 4500.0; // radius in nanometers (nm), from center of cell
		double pAngle = 90.0;
		double inc = 5.0; // increment in degrees
		double tAngle;
		double theta;  // angle in radians
		double phi;    // angle in radians
		double x, y, z;
		double jd;
		double maxSpine = 180.0;

		VrmlBuffer ver = new VrmlBuffer();

		ver.init();

		phi = pAngle / 180.0 * Math.PI; // convert degrees to radians
		ver.write( "PROTO RoughEr [] {\n" );
		ver.write( "Shape {\n" );
		ver.write( "geometry Extrusion {\n" );
		ver.write( "creaseAngle 1.57\n" );

		// cross section
		ver.write( "crossSection [\n" );
		ver.write( -250.0,  -1000.0 ); ver.write( ",\n" );
		ver.write( -220.0,   -500.0 ); ver.write( ",\n" );
		ver.write( -180.0,    800.0 ); ver.write( ",\n" );
		ver.write(    0.0,   1000.0 ); ver.write( ",\n" );
		ver.write(  170.0,    900.0 ); ver.write( ",\n" );
		ver.write(  200.0,   -600.0 ); ver.write( ",\n" );
		ver.write(  250.0,  -1000.0 ); ver.write( ",\n" );
		ver.write(  100.0,   -800.0 ); ver.write( ",\n" );
		ver.write( -150.0,   -800.0 ); ver.write( ",\n" );
		ver.write( -250.0,  -1000.0 ); ver.write( ",\n" );
		ver.write( "]\n" );

		// spine
		ver.write( "spine [\n" );
		for (tAngle = 0.0; tAngle <= maxSpine; tAngle+=inc) {
			theta = tAngle / 180.0 * Math.PI; // convert degrees to radians
			x = radius * Math.sin(phi) * Math.cos(theta);
			z = radius * Math.sin(phi) * Math.sin(theta);
			y = radius * Math.cos(phi);
			jd = random.nextDouble();
			jd *= (radius / 10.0);
			z += jd;
			ver.write( x, y, z );
			ver.write( ",\n" );
		}
		ver.write( "]\n" );

		// scale
		ver.write( "scale [\n" );
		ver.write( 1.0, 1.0 ); ver.write( ",\n" );
		for (tAngle = inc; tAngle < maxSpine; tAngle+=inc) {
			if (tAngle == maxSpine / 2.0) {
				ver.write( 0.1, 0.1 );
				ver.write( ",\n" );
			}
			else {
				jd = random.nextDouble();
				jd += 0.5;
				ver.write( jd, jd );
				ver.write( ",\n" );
			}
		}
		ver.write( 1.0, 1.0 ); ver.write( "\n" );
		ver.write( "] " );

		// appearance
		ver.write( "}\n" );
		ver.write( "appearance Appearance {\n");
		ver.write( "material Material {\n}\ntexture ImageTexture {\nurl \"roughEr.png\"\n}\n");
		ver.write( "}\n}\n}\n" );

		// Create VRML RoughEr.wrl
		//Writer erOut = MiscIo.openOutputFile(vrmlWriteDir + "RoughEr.wrl");
		//try {
		StringBuilder erOut = new StringBuilder();
			erOut.append("#VRML V2.0 utf8\n" );
			erOut.append( ver.get() );
		//} catch (IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
		//MiscIo.closeOutputFile(erOut);
		XholonGwtTabPanelHelper.addTab(erOut.toString(), "vrml", vrmlWriteDir + "RoughEr.wrl", false);
	}
	
	/**
	 * Write Smooth Endoplasmic Reticulum (ER) Proto file.
	 */
	protected void writeSmoothEr() {
		if (smoothErAlreadyWritten)
			return;
		smoothErAlreadyWritten = true;

		double radius = 4500.0; // radius in nanometers, from center of cell
		double pAngle = 90.0;
		double inc = 5.0; // increment in degrees
		double tAngle;
		double theta;  // angle in radians
		double phi;    // angle in radians
		double x, y, z;
		double jd;
		double minSpine = 75.0;
		double maxSpine = 100.0;
		VrmlBuffer ver = new VrmlBuffer();

		ver.init();

		phi = pAngle / 180.0 * Math.PI; // convert degrees to radians
		ver.write( "PROTO SmoothEr [] {\n" );
		ver.write( "Shape {\n" );
		ver.write( "geometry Extrusion {\n" );
		ver.write( "creaseAngle 1.57\n" );

		// cross section
		ver.write( "crossSection [\n" );
		ver.write( -450.0,  -1000.0 ); ver.write( ",\n" );
		ver.write( -420.0,   -500.0 ); ver.write( ",\n" );
		ver.write( -380.0,    800.0 ); ver.write( ",\n" );
		ver.write(    0.0,   1000.0 ); ver.write( ",\n" );
		ver.write(  370.0,    900.0 ); ver.write( ",\n" );
		ver.write(  300.0,   -600.0 ); ver.write( ",\n" );
		ver.write(  350.0,  -1000.0 ); ver.write( ",\n" );
		ver.write(  300.0,   -800.0 ); ver.write( ",\n" );
		ver.write( -350.0,   -800.0 ); ver.write( ",\n" );
		ver.write( -450.0,  -1000.0 ); ver.write( ",\n" );
		ver.write( "]\n" );

		// spine
		ver.write( "spine [\n" );
		for (tAngle = minSpine; tAngle <= maxSpine; tAngle+=inc) {
			theta = tAngle / 180.0 * Math.PI; // convert degrees to radians
			x = radius * Math.sin(phi) * Math.cos(theta);
			z = radius * Math.sin(phi) * Math.sin(theta);
			y = radius * Math.cos(phi);
			ver.write( x, y, z );
			ver.write( ",\n" );
		}
		ver.write( "]\n" );

		// scale
		ver.write( "scale [\n" );
		ver.write( 1.0, 1.0 ); ver.write( ",\n" );
		for (tAngle = inc; tAngle < maxSpine; tAngle+=inc) {
			if (tAngle == maxSpine / 2.0) {
				ver.write( 0.1, 0.1 );
				ver.write( ",\n" );
			}
			else {
				jd = random.nextDouble();
				jd += 0.5;
				ver.write( jd, jd );
				ver.write( ",\n" );
			}
		}
		ver.write( 1.0, 1.0 ); ver.write( "\n" );
		ver.write( "] " );

		// appearance
		ver.write( "}\n" );
		ver.write( "appearance Appearance { ");
		ver.write( "material Material {\n}\ntexture ImageTexture {\nurl \"smoothEr.png\"\n}\n");
		ver.write( "}\n}\n}\n" );

		// Create VRML SmoothEr.wrl
		//Writer erOut = MiscIo.openOutputFile(vrmlWriteDir + "SmoothEr.wrl");
		//try {
		StringBuilder erOut = new StringBuilder();
			erOut.append("#VRML V2.0 utf8\n" );
			erOut.append( ver.get() );
		//} catch (IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
		//MiscIo.closeOutputFile(erOut);
		XholonGwtTabPanelHelper.addTab(erOut.toString(), "vrml", vrmlWriteDir + "SmoothEr.wrl", false);
	}
	
	/**
	 * Write enzyme.
	 * @param xhNode An Enzyme Xholon.
	 */
	protected void writeEnzyme( IXholon xhNode ) {
		
		IXholon enzParent = xhNode.getParentNode();
		double enzRadius = 10.0;
		
		switch( enzParent.getXhcId() ) {
		case CytoplasmCE: // an enzyme in the Cytoplasm
			if (!isEnzymeRingInitialized) {
				resetEnzymeRing( 5000.0, 120.0 );
				isEnzymeRingInitialized = true;
			}
			enzRadius = 200.0;
			break;
		case MatrixCE: // an enzyme in Matrix in Mitochondrion
			if (!isEnzymeRingInitialized) {
				resetEnzymeRing( 1000.0, 90.0 );
				isEnzymeRingInitialized = true;
			}
			enzRadius = 100.0;
			break;
		default:
			System.out.println( "writeEnzyme: wrong type of parent" + enzParent.getName() );
			break;
		}
	
		double transparency = 0.0; // opaque
		double r, g, b;
		r = enzymeColorArray[enzymeIx].r;
		g = enzymeColorArray[enzymeIx].g;
		b = enzymeColorArray[enzymeIx].b;
		writeShape( xhNode, enzRadius, enzymeRing[enzymeIx], r, g, b, transparency );
		if (++enzymeIx >= MaxEnzymes) {
			enzymeIx = MaxEnzymes - 1;
			System.out.println( "writeEnzyme: enzymeIx > MaxEnzymes" );
		}
		IXholon enzRightSibling = xhNode.getNextSibling();
		if (enzRightSibling == null) {
			isEnzymeRingInitialized = false; // last enzyme in this ring
		}
		else {
			if (!(enzRightSibling.getXhc().hasAncestor("Enzyme"))) {
				isEnzymeRingInitialized = false; // last enzyme in this ring
			}
		}
	}
	
	/**
	 * Write random rotation.
	 */
	protected void writeRandomRotation() {
		v.write( "\nrotation " );
		SFRotation sfr = rot.get();
		v.write( sfr.x, sfr.y, sfr.z );
		v.write( " " );
		v.write( sfr.angle );
		v.write( "\n" );
	}
	
	/**
	 * Write lipids.
	 * @param xhNode CellBilayer Xholon.
	 */
	protected void writeLipids( IXholon xhNode ) {
		double qLipids = 0.0;
		RGBTypeDouble rgb = new RGBTypeDouble();
		double csm = 50.0; // Cross section multiplier for ring
		double pAngle = 90.0;
		double lRadius = 0.0;
		qLipids = 15707950000.0; //          <================ temporary fix
		// a lipid bilayer is a double layer
		// NumSmPerNmSquared lipids per square nanometer (nm)
		qLipids /= (NumSmPerNmSquared * 2.0);
		rgb.r = 1.0; rgb.g = 1.0; rgb.b = 0.0;
		lRadius = surfaceAreaToRadius(qLipids);
		//System.out.println( "writeLipids() " + xhNode.getName() + " " + qLipids + " " + lRadius );
		writeRing( xhNode, lRadius, pAngle, csm, rgb );
		writeSmallMolecules( xhNode, lRadius );
	}
	
	/**
	 * Reset Enzyme Ring.
	 * @param shellRadius Radius of the ring.
	 * @param pAngle Angle of the ring.
	 */
	protected void resetEnzymeRing( double shellRadius, double pAngle )
	{
		double inc = 360.0 / MaxEnzymes;
		doEnzymeRing( shellRadius, pAngle, inc, enzymeRing, MaxEnzymes );
		enzymeIx = 0;
		vColor.fillColorArray( enzymeColorArray, MaxEnzymes, VrmlColorCell.SvTypeEnzyme );
	}

	/**
	 * Write Solution.
	 * Write a ring that threads through all the small molecules
	 * and represents the solution (ex: Cytosol).
	 * @param xhNode Cytosol Xholon.
	 * @param shellRadius Radius of the ring.
	 */
	protected void writeSolution( IXholon xhNode, double shellRadius ) {
		RGBTypeDouble rgb = new RGBTypeDouble();
		double csm = 50.0; // Cross section multiplier for ring
		double pAngle = 90.0;
	
		rgb.r = 0.5; rgb.g = 0.5; rgb.b = 0.5; // grey
		writeRing( xhNode, shellRadius, pAngle, csm, rgb );
	}
	
	/**
	 * Write Water (the solvent).
	 * If the Water Xholon's parent is Cytosol
	 *     Then create a water ring
	 * Else
	 *     Create a normal small molecule shape.
	 * TODO should also include counts for all organelles.
	 * @param xhNode Water Xholon.
	 */
	protected void writeWater( IXholon xhNode ) {
		if (xhNode.getParentNode().getXhcId() != CytosolCE) {
			// write shape here, or when do other small molecules ?
			v.write( "children [\n" ); // default
			return;
		}
		double qWater      = 0.0;
		double qAllNoWater = 0.0;
		double qTotal      = 0.0;
		double csm = 50.0; // Cross section multiplier for ring
		double pAngle = 90.0;
		int pH_Int = 7; // neutral pH by default
	
		qWater = ((XhLife)xhNode).getPheneVal();
		Vector smV = xhNode.getSiblings(); // get siblings, excluding self
		XhLife xhSib = null;
		//while (smIt.hasNext()) {
		//	xhSib = (XhLife)smIt.next();
		for (int i = 0; i < smV.size(); i++) {
			xhSib = (XhLife)smV.elementAt(i);
			// add up pheneVal of all passive objects that are siblings of Water (in the same compartment)
			if (xhSib.isPassiveObject()) {
				qAllNoWater += xhSib.getPheneVal();
				if (xhSib.getXhcId() == HCE) { // Hydrogen ion
					pH_Int = (int)Math.floor(pH(xhSib.getPheneVal(), qWater)); // get pH
				}
			}
		}
		qTotal = qWater + qAllNoWater + getNucleusVolume();
		//System.out.println( "writeWater(): " + xhNode.getParent().getName() + " " + qWater + " " + qAllNoWater + " " + qTotal );
		//System.out.println( "writeWater(): pH_Int=" + pH_Int );
		if (pH_Int < 0) pH_Int = 0;
		if (pH_Int > 14) pH_Int = 14;
		writeRing( xhNode, volumeToRadius(qTotal), pAngle, csm, pHcolorArray[pH_Int] );
	}
	
	/**
	 * Write Small Molecules (the solutes dissolved in water solvent).
	 * Needs to do a translation before writing each shape.
	 * @param xhNode A Xholon, such as a Cytosol or CellBilayer.
	 * @param shellRadius Radius of the ring that will be written out.
	 */
	protected void writeSmallMolecules( IXholon xhNode, double shellRadius ) {
		double qMol = 0.0;
		double transparency = 0.0;
		double smRadius = 0.0;
		double shellRadiusInVic = shellRadius * 1.1; // "in vicinity"
		double pAngle = 90.0;
		
		int smSize = xhNode.getNumChildren(false); // get number of children
		if (smSize == 0) {
			return; // there are no SM to write
		}
		double inc = 360.0 / smSize;
		int i;
		RGBTypeDouble ca[] = new RGBTypeDouble[smSize];
		SFVec3f locSM[] = new SFVec3f[smSize]; // locations of SM shapes
		SFVec3f locInVic[] = new SFVec3f[smSize]; // locations of SM shapes "in vicinity"
		for (i = 0; i < smSize; i++) {
			ca[i] = new RGBTypeDouble();
			locSM[i] = new SFVec3f();
			locInVic[i] = new SFVec3f();
		}
		
		vColor.fillColorArray( ca, smSize, VrmlColorCell.SvTypeSmallMolecule );
		doRing( shellRadius, pAngle, inc, locSM, smSize );
		doRing( shellRadiusInVic, pAngle, inc, locInVic, smSize );
	
		Vector smV = xhNode.getChildNodes(false); // get children; probably all small molecules
		XhLife xhSM;
		int ix = 0;
		//while (smIt.hasNext()) {
		//	xhSM = (XhLife)smIt.next();
		for (i = 0; i < smV.size(); i++) {
			xhSM = (XhLife)smV.elementAt(i);
			if (xhSM.getXhcId() != WaterCE) { // water is handled elsewhere
				qMol = xhSM.getPheneVal();
				qMol /= NumSmPerNmSquared; // NumSmPerNmSquared small molecules per nanometer
				smRadius = volumeToRadius( qMol );
				//System.out.println("radius:" + smRadius);
				//smRadius = 290; // temporary
				transparency = 0.0; // opaque
				v.writeDefNodeName( xhSM.getName() );
				if (xhSM.getXhcId() == PE_InVicinityCE) { // move this lipid further outward in Cytosol
					writeShape( xhSM, smRadius, locInVic[ix-1], ca[ix].r, ca[ix].g, ca[ix].b, transparency );
				}
				else {
					writeShape( xhSM, smRadius, locSM[ix], ca[ix].r, ca[ix].g, ca[ix].b, transparency );
				}
				v.write("]\n}\n");
			}
			ix++;
		}
	}
	
	/**
	 * Write a solid ring.
	 * @param xhNode Xholon node.
	 * @param radius Radius of the ring.
	 * @param pAngle Angle of the ring.
	 * @param crossSectionMultiplier Determines the thickness of the ring strand.
	 * @param rgb RGB color.
	 */
	protected void writeRing( IXholon xhNode,
							    double radius,
							    double pAngle,
							    double crossSectionMultiplier,
							    RGBTypeDouble rgb )
	{
		double inc = 10.0; // increment in degrees
		double tAngle;
		double theta;  // angle in radians
		double phi;    // angle in radians
		double x, y, z;
		double maxSpine = 360.0;
		double m = crossSectionMultiplier;
		double transparency = 0.0; // opaque

		phi = pAngle / 180.0 * Math.PI; // convert degrees to radians
		v.write( "children [\n" );
		v.write( "Shape {\n" );
		v.write( "geometry Extrusion {\n" );
		v.write( "creaseAngle 1.57\n" );
		v.write( "beginCap FALSE\n" );
		v.write( "endCap   FALSE\n" );

		// cross section
		v.write( "crossSection [\n" );
		v.write( "# circle\n" );
		v.write(  1.00*m,  0.00*m ); v.write( ",\n" );
		v.write(  0.92*m, -0.38*m ); v.write( ",\n" );
		v.write(  0.71*m, -0.71*m ); v.write( ",\n" );
		v.write(  0.38*m, -0.92*m ); v.write( ",\n" );
		v.write(  0.00*m, -1.00*m ); v.write( ",\n" );
		v.write( -0.38*m, -0.92*m ); v.write( ",\n" );
		v.write( -0.71*m, -0.71*m ); v.write( ",\n" );
		v.write( -0.92*m, -0.38*m ); v.write( ",\n" );
		v.write( -1.00*m, -0.00*m ); v.write( ",\n" );
		v.write( -0.92*m,  0.38*m ); v.write( ",\n" );
		v.write( -0.71*m,  0.71*m ); v.write( ",\n" );
		v.write( -0.38*m,  0.92*m ); v.write( ",\n" );
		v.write(  0.00*m,  1.00*m ); v.write( ",\n" );
		v.write(  0.38*m,  0.92*m ); v.write( ",\n" );
		v.write(  0.71*m,  0.71*m ); v.write( ",\n" );
		v.write(  0.92*m,  0.38*m ); v.write( ",\n" );
		v.write(  1.00*m,  0.00*m ); v.write( "\n" );
		v.write( "]\n" );

		// spine
		v.write( "spine [\n" );
		for (tAngle = 0.0; tAngle <= maxSpine; tAngle+=inc) {
			theta = tAngle / 180.0 * Math.PI; // convert degrees to radians
			x = radius * Math.sin(phi) * Math.cos(theta);
			y = radius * Math.sin(phi) * Math.sin(theta);
			z = radius * Math.cos(phi);
			v.write( x, y, z );
			v.write( ",\n" );
		}
		v.write( "]\n" );

		// appearance
		v.write( "}\n" );
		v.write( "appearance Appearance {\n");
		v.write( "material Material {\ndiffuseColor ");
		v.write( rgb.r, rgb.g, rgb.b );
		v.write( " transparency ");
		v.write( transparency );
		v.write( "\n}\n}\n}\n" );
	} // end writeRing


	/**
	 * Return radius given a quantity (a surface area).
	 * ex: to calculate radius given quantity of lipids in CellBilayer.
	 * @param quantity Surface area (square nm).
	 * @return Radius (nm).
	 */
	protected double surfaceAreaToRadius( double quantity )
	{
		return( Math.sqrt( quantity / (Math.PI * 4.0) ));
	}

	/**
	 * Return radius given a quantity (a volume).
	 * ex: to calculate radius given quantity of water in Cytosol.
	 * @param quantity Volume (cubed nm).
	 * @return Radius (nm).
	 */
	protected double volumeToRadius( double quantity )
	{
		return( cbrt( (quantity * 3) / (Math.PI * 4.0) ));
	}

	/**
	 * Calculate cube root.
	 * @param x Some quantity.
	 * @return The cube root.
	 */
	protected double cbrt( double x )
	{
	  return Math.exp( Math.log( x ) / 3.0 );
	}

	/**
	 * Calculate pH of a solution. 
	 * qH is the quantity of hydrogen ions (H+).
	 * qWater is quantity of water molecules.
	 * @param qH Quantity of hydrogen ions.
	 * @param qWater Quantity of water molecules.
	 * @return The pH.
	 */
	protected double pH( double qH, double qWater )
	{
		// H+ concentration is in grams of H+ per liter of water
		double hIonConcentration = qH / (qWater / 1000.0);
		double pH = Math.log( 1.0 / hIonConcentration ); // was log10() in RRT Cell Model  2005
		//System.out.println( "qH=" + qH + "qWater=" + qWater + "pH=" + pH );
		return pH;
	}
	
	/**
	 * Test the pH function.
	 * TODO make this part of a JUnit test case.
	 */
	protected void test_pH() {
		pH( 1.0, 1000.0 );
		pH( 1.0, 1000000.0 );
		pH( 1.0, 10000000000.0 );
		pH( 1.0, 10000000000000.0 );
		pH( 1.0, 100000000000000000.0 );
		pH( 4727.0, 47269293981481.5 );
	}

	/**
	 * Set up a complete 360 degree ring of locations.
	 * @param radius Radius of the ring.
	 * @param pAngle Angle of the ring (degrees).
	 * @param inc Amount to increment the angle for each curve segment.
	 * @param loc A set of XYZ structures that are created by this function.
	 * @param maxLocs The maximum size of loc.
	 */
	protected void doRing( double radius, double pAngle,
							 double inc, SFVec3f loc[], int maxLocs)
	{
		double tAngle;
		double theta;  // angle in radians ("longitude")
		double phi;    // angle in radians ("latitude")
		int next = 0;
		phi = pAngle / 180.0 * Math.PI;
		for (tAngle = 0.0; tAngle < 360.0; tAngle+=inc) {
			if (next >= maxLocs) break;
			theta = tAngle / 180.0 * Math.PI;
			loc[next].x = radius * Math.sin(phi) * Math.cos(theta);
			loc[next].y = radius * Math.sin(phi) * Math.sin(theta);
			loc[next].z = radius * Math.cos(phi);
			next++;
		}
	}

	/**
	 * Set up a complete 360 degree ring of locations.
	 * @param radius Radius of the ring.
	 * @param pAngle Angle of the ring (degrees).
	 * @param inc Amount to increment the angle for each curve segment.
	 * @param loc A set of XYZ structures that are created by this function.
	 * @param maxLocs The maximum size of loc.
	 */
	protected void doEnzymeRing( double radius, double pAngle,
							 double inc, SFVec3f loc[], int maxLocs)
	{
		// Note that z and y are reversed.
		double tAngle;
		double theta;  // angle in radians ("longitude")
		double phi;    // angle in radians ("latitude")
		int next = 0;
		phi = pAngle / 180.0 * Math.PI;
		for (tAngle = 0.0; tAngle < 360.0; tAngle+=inc) {
			if (next >= maxLocs) break;
			theta = tAngle / 180.0 * Math.PI;
			loc[next].x = radius * Math.sin(phi) * Math.cos(theta);
			loc[next].z = radius * Math.sin(phi) * Math.sin(theta);
			loc[next].y = radius * Math.cos(phi);
			next++;
		}
	}
	
}
