/**
 * Classes and functions and data that are or may be of use in the Data4Good BGCO project.
 * bgco01.js
 * Ken Webb  January 15, 2019
 * MIT License, Copyright (C) 2019 Ken Webb
 * 
 * This code is referenced by a script tag in XholonD4gBgco.html .
*/

if (typeof xh == "undefined") {
  xh = {};
}

(function() {
  // is the following necessary ?
  if (typeof d3 == "undefined") {return;}
  
  $wnd = window;
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
  // functions used in PhysicalSystembehavior.js
  // source: https://www.thelists.org/list-of-leap-years.html
  
  const LEAPYEARS = [1992,1996,2000,2004,2008,2012,2016,2020,2024];

  // create a line chart that plots the members moving between the place lines  20110553
  $wnd.xh.initTimelinePlot = function ($this) {
    var xhRoot = $wnd.xh.xpath(".", $this.parent()); // or ./Members ?  or City ?
    var dpp = "BGCO Member Attendance,Time (YYYYdddh),children moving between places,./statistics/,stats,1,WRITE_AS_INT";
    var mode = "new";
    var dp = "gnuplot"; // gnuplot google2
    var plotStr = '<Plot mode="' + mode + '" dataPlotter="' + dp + '" dataPlotterParams="' + dpp + '"></Plot>';
    xhRoot.append(plotStr);
  }
  
  // control animation
  $wnd.xh.controlAnimation = function() {
    var root = $wnd.xh.root();
    var ani = root.xpath("descendant::Animate");
    ani.action("Pause animation"); // pauses the visible animation, but not the model execution itself
    ani.action("Faster"); // speeds up the model execution, and the animation
    ani.action("Faster");
    ani.action("Faster");
    // also avavilable are:
    //ani.action("Slower");
    //ani.action("Unpause animation");
    // OR
    // $wnd.xh.root().xpath("descendant::Animate").action("Pause animation").action("Faster").action("Faster").action("Faster");
  }
  
  // is this a leap year?
  $wnd.xh.isLeapyear = function(year) {
    var bval = LEAPYEARS.indexOf(year) != -1;
    //me.println("isLeapyear " + year + " " + bval);
    return bval;
  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
  // specify AttendanceTaker behavior
  const OPAC_MAX = 0.7;
  // a value of 1.0 for both MULTIPLIERs will ensure that there is no gradual fading and highlighting of the nodes in the d3cp animation
  //const OPAC_FADE_MULTIPLIER = 0.999; // unused for now
  const OPAC_HILT_MULTIPLIER = 1.1;
  const DO_ATTENDANCE_TAKER_BEHAVIOR = true;
  $wnd.console.log("Running JS library copy of $wnd.xh.AttendanceTakerbehavior");
  
  $wnd.xh.AttendanceTakerbehavior = function AttendanceTakerbehavior() {}
  
  $wnd.xh.AttendanceTakerbehavior.prototype.postConfigure = function() {
    this.ataker = this.cnode.parent();
    //this.ataker.atakername = this.ataker.name();
    this.clubhouseabbrev = "";
    this.programname = "";
    //var programid = "clubhouseid";
    if (this.ataker.xhc().name() == "Program") {
      //programid = "programid";
      this.clubhouseabbrev = this.ataker.parent().role().split(" ")[0]; // ex: "PYC"
      this.programname = this.ataker.role();
    }
    else {
      this.clubhouseabbrev = this.ataker.role().split(" ")[0]; // ex: "PYC"
      this.programname = "clubhouse";
    }
    this.ataker.attendance = ""; //"memberid,datetime," + programid;
    this.rgba2rgbOpacity(this.ataker);
  };
  
  $wnd.xh.AttendanceTakerbehavior.prototype.act = function() {
    //this.ataker.println("Taking attendance ...");
    var ts = 0;
    var node = this.cnode.next();
    var opac = this.ataker.opacity();
    if (node) {
      ts = $wnd.xh.param("TimeStep");
    }
    else {
      //this.ataker.opacity(opac * OPAC_FADE_MULTIPLIER); // fade 0.9
    }
    while (node) {
      //this.ataker.attendance += "\n" + node.name() + "," + ts + "," + this.ataker.atakername;
      // 2011039,Andrea,PYC,Club
      // 2011039,Andrea,PYC,Homework
      if ($wnd.xh.AttendanceTakerbehavior.prototype.doAttendanceTakerbehavior) {
        this.ataker.attendance += "\n" + ts + "," + node.role() + "," + this.clubhouseabbrev + "," + this.programname;
      }
      if (opac <= OPAC_MAX) {
        opac = opac * OPAC_HILT_MULTIPLIER;
        this.ataker.opacity(opac); // highlight 1.01
      }
      node = node.next();
    }
    // move this.cnode past the latest arrivals
    this.ataker.append(this.cnode.remove());
  };
  
  // rgba() to rgb() + opacity (for use in Movie Script Parser)
  // ex: rgba(20,220,60,1.0) becomes rgb(20,220,60) and 1.0
  $wnd.xh.AttendanceTakerbehavior.prototype.rgba2rgbOpacity = function(node) {
    var color = node.xhc().color();
    if (color.substring(0,5) == "rgba(") {
      var rgba = color.substring(5, color.length - 1).split(",");
      var rgb = "rgb(" + rgba[0] + "," + rgba[1] + "," + rgba[2] + ")";
      var a = rgba[3];
      node.color(rgb);
      node.opacity(a);
    }
  };
  
  $wnd.xh.AttendanceTakerbehavior.prototype.doAttendanceTakerbehavior = DO_ATTENDANCE_TAKER_BEHAVIOR; // whether or not to do the attendance taking
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  // Attend
  $wnd.xh.attend = {};
  
  /**
   * Gather all the Clubhouse and Program attendance data, that may have been collected by AttendanceTakerbehavior nodes.
   * @param pcodes the Xholon app <PostalCodes> node.
   */
  $wnd.xh.attend.gatherAttendData = function(pcodes) {
    pcodes.println(this);
    // 20052713,Andrea,MC,clubhouse
    var arr = ["datetime,member,clubhouse,program"];
    var pcode = pcodes.first();
    while (pcode) {
      var chouse = pcode.first();
      while (chouse) {
        if (chouse.xhc().name() == "Clubhouse") {
          var data = chouse.attendance;
          if (data) {
            arr.push(data);
          }
          var program = chouse.first();
          while (program) {
            if (program.xhc().name() == "Program") {
              var data = program.attendance;
              if (data) {
                arr.push(data);
              }
          	}
            program = program.next();
          }
        }
        chouse = chouse.next();
      }
      pcode = pcode.next();
    }
    //pcodes.println(arr);
    for (var i = 0; i < arr.length; i++) {
      pcodes.print(arr[i]);
    }
    pcodes.print("\n");
  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  // Schools
  $wnd.xh.schools = {};
  
  // Schools csv
  // source: Ottawa Schools v3.csv
  $wnd.xh.schools.csv = `
﻿Active,SchoolBoardAcronym,SchoolType,SchoolNameEnglish,FullAddress,StreetAddress,City,Prov,PostalCode
Y,OCDSB,Secondary,A.Y. Jackson Secondary School,"150 Abbeyhill, Kanata, Ontario, K2L 1H7",150 Abbeyhill,Kanata,Ontario,K2L 1H7
Y,OCDSB,Secondary,Bell High School,"40 Cassidy, Nepean, Ontario, K2H 6K1",40 Cassidy,Nepean,Ontario,K2H 6K1
Y,OCDSB,Secondary,Brookfield High School,"824 Brookfield, Ottawa, Ontario, K1V 6J3",824 Brookfield,Ottawa,Ontario,K1V 6J3
Y,OCDSB,Secondary,Cairine Wilson Secondary School,"975 Orleans, Orleans, Ontario, K1C 2Z5",975 Orleans,Orleans,Ontario,K1C 2Z5
Y,OCDSB,Secondary,Canterbury High School,"900 Canterbury, Ottawa, Ontario, K1G 3A7",900 Canterbury,Ottawa,Ontario,K1G 3A7
Y,OCDSB,Secondary,Colonel By Secondary School,"2381 Ogilvie, Gloucester, Ontario, K1J 7N4",2381 Ogilvie,Gloucester,Ontario,K1J 7N4
Y,OCDSB,Secondary,Earl of March Secondary School,"No. 4 The Parkway, Kanata, Ontario, K2K 1Y4",No. 4 The Parkway,Kanata,Ontario,K2K 1Y4
Y,OCDSB,Secondary,Elizabeth Wyn Wood Secondary Alternate,"20 Rossland, Nepean, Ontario, K2G 1H6",20 Rossland,Nepean,Ontario,K2G 1H6
Y,OCDSB,Secondary,Frederick Banting Secondary Alternate Pr,"220, 1453 Stittsville Main, Stittsville, Ontario, K2S 1A3","220, 1453 Stittsville Main",Stittsville,Ontario,K2S 1A3
Y,OCDSB,Secondary,Glebe Collegiate Institute,"212 Glebe, Ottawa, Ontario, K1S 2C9",212 Glebe,Ottawa,Ontario,K1S 2C9
Y,OCDSB,Secondary,Gloucester High School,"2060 Ogilvie, Gloucester, Ontario, K1J 7N8",2060 Ogilvie,Gloucester,Ontario,K1J 7N8
Y,OCDSB,Secondary,Hillcrest High School,"1900 Dauphin, Ottawa, Ontario, K1G 2L7",1900 Dauphin,Ottawa,Ontario,K1G 2L7
Y,OCDSB,Secondary,John McCrae Secondary School,"103 Malvern, Nepean, Ontario, K2J 4T2",103 Malvern,Nepean,Ontario,K2J 4T2
Y,OCDSB,Secondary,Lisgar Collegiate Institute,"29 Lisgar, Ottawa, Ontario, K2P 0B9",29 Lisgar,Ottawa,Ontario,K2P 0B9
Y,OCDSB,Secondary,Longfields Davidson Heights Secondary School,"149 Berrigan, Nepean, Ontario, K2J 5C6",149 Berrigan,Nepean,Ontario,K2J 5C6
Y,OCDSB,Secondary,Merivale High School,"1755 Merivale, Nepean, Ontario, K2G 1E2",1755 Merivale,Nepean,Ontario,K2G 1E2
Y,OCDSB,Secondary,Nepean High School,"574 Broadview, Ottawa, Ontario, K2A 3V8",574 Broadview,Ottawa,Ontario,K2A 3V8
Y,OCDSB,Secondary,Norman Johnston Secondary Alternate Prog,"2401 Cléroux, Ottawa, Ontario, K1W 1A1",2401 Cléroux,Ottawa,Ontario,K1W 1A1
Y,OCDSB,Secondary,Osgoode Township High School,"2800 8th Line, Metcalfe, Ontario, K0A 2P0",2800 8th Line,Metcalfe,Ontario,K0A 2P0
Y,OCDSB,Secondary,Ottawa Technical Secondary School,"485 Donald, Ottawa, Ontario, K1K 1L8",485 Donald,Ottawa,Ontario,K1K 1L8
Y,OCDSB,Secondary,Richard Pfaff Secondary Alternate Site,"160 Percy, Ottawa, Ontario, K1R 6E5",160 Percy,Ottawa,Ontario,K1R 6E5
Y,OCDSB,Secondary,Rideau High School,"815 St Laurent, Ottawa, Ontario, K1K 3A7",815 St Laurent,Ottawa,Ontario,K1K 3A7
Y,OCDSB,Secondary,Ridgemont High School,"2597 Alta Vista, Ottawa, Ontario, K1V 7T3",2597 Alta Vista,Ottawa,Ontario,K1V 7T3
Y,OCDSB,Secondary,Sir Guy Carleton Secondary School,"55 Centrepointe, Nepean, Ontario, K2G 5L4",55 Centrepointe,Nepean,Ontario,K2G 5L4
Y,OCDSB,Secondary,Sir Robert Borden High School,"131 Greenbank, Nepean, Ontario, K2H 8R1",131 Greenbank,Nepean,Ontario,K2H 8R1
Y,OCDSB,Secondary,Sir Wilfrid Laurier Secondary School,"1515 Tenth Line, Orleans, Ontario, K1E 3E8",1515 Tenth Line,Orleans,Ontario,K1E 3E8
Y,OCDSB,Secondary,South Carleton High School,"3673 McBean, Richmond, Ontario, K0A 2Z0",3673 McBean,Richmond,Ontario,K0A 2Z0
Y,OCDSB,Secondary,Urban Aboriginal Alternate High School,"440 Percy, Ottawa, Ontario, K1R 6E5",440 Percy,Ottawa,Ontario,K1R 6E5
Y,OCDSB,Secondary,West Carleton Secondary School,"RR 2, 3088 Dunrobin, Dunrobin, Ontario, K0A 1T0","RR 2, 3088 Dunrobin",Dunrobin,Ontario,K0A 1T0
Y,OCDSB,Secondary,Woodroffe High School,"2410 Georgina, Ottawa, Ontario, K2B 7M8",2410 Georgina,Ottawa,Ontario,K2B 7M8
Y,OCSB,Secondary,All Saints Catholic High School,"5115 Kanata, Kanata, Ontario, K2K 3K5",5115 Kanata,Kanata,Ontario,K2K 3K5
Y,OCSB,Secondary,Holy Trinity Catholic High School,"180 Katimavik, Kanata, Ontario, K2L 4A7",180 Katimavik,Kanata,Ontario,K2L 4A7
Y,OCSB,Secondary,Immaculata High School,"140 Main, Ottawa, Ontario, K1S 5P4",140 Main,Ottawa,Ontario,K1S 5P4
Y,OCSB,Secondary,Lester B Pearson Catholic High School,"2072 Jasmine, Gloucester, Ontario, K1J 8M5",2072 Jasmine,Gloucester,Ontario,K1J 8M5
Y,OCSB,Secondary,Mother Teresa High School,"440 Longfields, Nepean, Ontario, K2J 4T1",440 Longfields,Nepean,Ontario,K2J 4T1
Y,OCSB,Secondary,Notre Dame High School,"710 Broadview, Ottawa, Ontario, K2A 2M2",710 Broadview,Ottawa,Ontario,K2A 2M2
Y,OCSB,Secondary,Sacred Heart High School,"5870 Abbott, Stittsville, Ontario, K2S 1X4",5870 Abbott,Stittsville,Ontario,K2S 1X4
Y,OCSB,Secondary,St Joseph High School,"3333 Greenbank, Nepean, Ontario, K2J 4J1",3333 Greenbank,Nepean,Ontario,K2J 4J1
Y,OCSB,Secondary,St Mark High School,"1040 Dozois, Manotick, Ontario, K4M 1B2",1040 Dozois,Manotick,Ontario,K4M 1B2
Y,OCSB,Secondary,St Matthew High School,"6550 Bilberry, Orleans, Ontario, K1C 2S9",6550 Bilberry,Orleans,Ontario,K1C 2S9
Y,OCSB,Secondary,St Nicholas Adult High School,"893 Admiral, Ottawa, Ontario, K1Z 6L6",893 Admiral,Ottawa,Ontario,K1Z 6L6
Y,OCSB,Secondary,St Patrick's High School,"2525 Alta Vista, Ottawa, Ontario, K1V 7T3",2525 Alta Vista,Ottawa,Ontario,K1V 7T3
Y,OCSB,Secondary,St Paul High School,"2675 Draper, Ottawa, Ontario, K2H 7A1",2675 Draper,Ottawa,Ontario,K2H 7A1
Y,OCSB,Secondary,St Peter High School,"750 Charlemagne, Orleans, Ontario, K4A 3M4",750 Charlemagne,Orleans,Ontario,K4A 3M4
Y,OCSB,Secondary,St Pius X High School,"1481 Fisher, Ottawa, Ontario, K2C 1X4",1481 Fisher,Ottawa,Ontario,K2C 1X4
Y,OCSB,Secondary,St. Francis Xavier (9-12) Catholic School,"3740 Spratt, Gloucester, Ontario, K1V 2M1",3740 Spratt,Gloucester,Ontario,K1V 2M1
Y,CECCE,Secondary,École secondaire catholique Académie catholique Ange-Gabriel,"1515 Kensington, Brockville, Ontario, K6V 6H9",1515 Kensington,Brockville,Ontario,K6V 6H9
Y,CECCE,Secondary,École secondaire catholique Béatrice-Desloges,"1999 Provence, Orléans, Ontario, K4A 3Y6",1999 Provence,Orléans,Ontario,K4A 3Y6
Y,CECCE,Secondary,École secondaire catholique Centre professionnel et technique Minto,"801 de l'Aviation, Ottawa, Ontario, K1K 4R3",801 de l'Aviation,Ottawa,Ontario,K1K 4R3
Y,CECCE,Secondary,École secondaire catholique Collège catholique Franco-Ouest,"411 Seyton, Nepean, Ontario, K2H 8X1",411 Seyton,Nepean,Ontario,K2H 8X1
Y,CECCE,Secondary,École secondaire catholique Collège catholique Samuel-Genest,"704 Carson's, Ottawa, Ontario, K1K 2H3",704 Carson's,Ottawa,Ontario,K1K 2H3
Y,CECCE,Secondary,École secondaire catholique Franco-Cité,"623, Smyth, Ottawa, Ontario, K1G 1N7",623 Smyth,Ottawa,Ontario,K1G 1N7
Y,CECCE,Secondary,École secondaire catholique Garneau,"6588 Carrière, Orléans, Ontario, K1C 1J4",6588 Carrière,Orléans,Ontario,K1C 1J4
Y,CECCE,Secondary,École secondaire catholique Jeanne-Lajoie,"1257 Pembroke Ouest, Pembroke, Ontario, K8A 5R3",1257 Pembroke Ouest,Pembroke,Ontario,K8A 5R3
Y,CECCE,Secondary,École secondaire catholique Marie-Rivier,"711 Dalton, Kingston, Ontario, K7M 8N6",711 Dalton,Kingston,Ontario,K7M 8N6
Y,CECCE,Secondary,École secondaire catholique Orléans,"6401 Renaud, Orléans, Ontario, K1W 0H8",6401 Renaud,Orléans,Ontario,K1W 0H8
Y,CECCE,Secondary,École secondaire catholique Paul-Desmarais,"5315 Abbott, Ottawa, Ontario, K2S 0X3",5315 Abbott,Ottawa,Ontario,K2S 0X3
Y,CECCE,Secondary,École secondaire catholique Pavillon secondaire de Kemptville,"K0G 1J1, 830 Prescott, Kemptville, Ontario, K0G 1J0","830 Prescott, Kemptville, Ontario, K0G 1J0",Kemptville,Ontario,K0G 1J0
Y,CECCE,Secondary,École secondaire catholique Pierre-Savard,"1110 Longfields, Nepean, Ontario, K2J 0H9",1110 Longfields,Nepean,Ontario,K2J 0H9
Y,CEPEO,Secondary,École secondaire des adultes Le Carrefour,"2445 St-Laurent, Ottawa, Ontario, K1G 6C3",2445 St-Laurent,Ottawa,Ontario,K1G 6C3
Y,CEPEO,Secondary,École secondaire publique De La Salle,"501 ancienne St-Patrick, Ottawa, Ontario, K1N 8R3",501 ancienne St-Patrick,Ottawa,Ontario,K1N 8R3
Y,CEPEO,Secondary,École secondaire publique Gisèle-Lalonde,"500 Millennium, Orléans, Ontario, K4A 4X3",500 Millennium,Orléans,Ontario,K4A 4X3
Y,CEPEO,Secondary,École secondaire publique L'Alternative,"2445 St-Laurent, Ottawa, Ontario, K1G 6C3",2445 St-Laurent,Ottawa,Ontario,K1G 6C3
Y,CEPEO,Secondary,École secondaire publique Louis-Riel,"1655 Bearbrook, Gloucester, Ontario, K1B 4N3",1655 Bearbrook,Gloucester,Ontario,K1B 4N3
Y,CEPEO,Secondary,École secondaire publique Maurice-Lapointe,"17 Bridgestone, Kanata, Ontario, K2M 0E9",17 Bridgestone,Kanata,Ontario,K2M 0E9
Y,CEPEO,Secondary,École secondaire publique Omer-Deslauriers,"159 Chesterton, Ottawa, Ontario, K2E 7E6",159 Chesterton,Ottawa,Ontario,K2E 7E6
Y,OCDSB,Elementary,A. Lorne Cassidy Elementary School,"27 Hobin, Stittsville, Ontario, K2S 1G8",27 Hobin,Stittsville,Ontario,K2S 1G8
Y,OCDSB,Elementary,Adrienne Clarkson Elementary School,"170 Stoneway, Nepean, Ontario, K2G 6R2",170 Stoneway,Nepean,Ontario,K2G 6R2
Y,OCDSB,Elementary,Agincourt Road Public School,"1250 Agincourt, Ottawa, Ontario, K2C 2J2",1250 Agincourt,Ottawa,Ontario,K2C 2J2
Y,OCDSB,Elementary,Alta Vista Public School,"1349 Randall, Ottawa, Ontario, K1H 7R2",1349 Randall,Ottawa,Ontario,K1H 7R2
Y,OCDSB,Elementary,Arch Street Public School,"2129 Arch, Ottawa, Ontario, K1G 2H5",2129 Arch,Ottawa,Ontario,K1G 2H5
Y,OCDSB,Elementary,Avalon II Elementary School,"2350 Portobello Boulevard, Orléans, Ontario, K4A 0W3",2350 Portobello Boulevard,Orléans,Ontario,K4A 0W3
Y,OCDSB,Elementary,Avalon Public School,"2080 Portobello, Orleans, Ontario, K4A 0K5",2080 Portobello,Orleans,Ontario,K4A 0K5
Y,OCDSB,Elementary,Barrhaven Public School,"80 Larkin, Nepean, Ontario, K2J 1B7",80 Larkin,Nepean,Ontario,K2J 1B7
Y,OCDSB,Elementary,Bayshore Public School,"145 Woodridge, Nepean, Ontario, K2B 7T2",145 Woodridge,Nepean,Ontario,K2B 7T2
Y,OCDSB,Elementary,Bayview Public School,"185 Owl, Ottawa, Ontario, K1V 9K3",185 Owl,Ottawa,Ontario,K1V 9K3
Y,OCDSB,Elementary,Bells Corners Public School,"3770 Richmond, Nepean, Ontario, K2H 5C3",3770 Richmond,Nepean,Ontario,K2H 5C3
Y,OCDSB,Elementary,Berrigan Elementary School,"199 Berrigan, Nepean, Ontario, K2J 5C6",199 Berrigan,Nepean,Ontario,K2J 5C6
Y,OCDSB,Elementary,Blossom Park Public School,"3810 Sixth, Gloucester, Ontario, K1T 1K6",3810 Sixth,Gloucester,Ontario,K1T 1K6
Y,OCDSB,Elementary,Briargreen Public School,"19 Parkfield, Nepean, Ontario, K2G 0R9",19 Parkfield,Nepean,Ontario,K2G 0R9
Y,OCDSB,Elementary,Bridlewood Community Elementary School,"63 Bluegrass, Kanata, Ontario, K2M 1G2",63 Bluegrass,Kanata,Ontario,K2M 1G2
Y,OCDSB,Elementary,Broadview Public School,"590 Broadview, Ottawa, Ontario, K2A 2L8",590 Broadview,Ottawa,Ontario,K2A 2L8
Y,OCDSB,Elementary,Cambridge Street Community Public School,"250 Cambridge, Ottawa, Ontario, K1R 7B2",250 Cambridge,Ottawa,Ontario,K1R 7B2
Y,OCDSB,Elementary,Carleton Heights Public School,"1660 Prince of Wales, Ottawa, Ontario, K2C 1P4",1660 Prince of Wales,Ottawa,Ontario,K2C 1P4
Y,OCDSB,Elementary,Carson Grove Elementary School,"1401 Matheson, Gloucester, Ontario, K1J 8B5",1401 Matheson,Gloucester,Ontario,K1J 8B5
Y,OCDSB,Elementary,Castlefrank Elementary School,"55 McCurdy, Kanata, Ontario, K2L 4A9",55 McCurdy,Kanata,Ontario,K2L 4A9
Y,OCDSB,Elementary,Castor Valley Elementary School,"2630 Grey's Creek, Greely, Ontario, K4P 1N2",2630 Grey's Creek,Greely,Ontario,K4P 1N2
Y,OCDSB,Elementary,Cedarview Middle School,"2760 Cedarview, Nepean, Ontario, K2J 4J2",2760 Cedarview,Nepean,Ontario,K2J 4J2
Y,OCDSB,Elementary,Centennial Public School,"376 Gloucester, Ottawa, Ontario, K1R 5E8",376 Gloucester,Ottawa,Ontario,K1R 5E8
Y,OCDSB,Elementary,Century Public School,"8 Redpine, Nepean, Ontario, K2E 6S9",8 Redpine,Nepean,Ontario,K2E 6S9
Y,OCDSB,Elementary,Chapman Mills Elementary School,"260 Leamington, Ottawa, Ontario, K2J 3V1",260 Leamington,Ottawa,Ontario,K2J 3V1
Y,OCDSB,Elementary,Charles H. Hulse Public School,"2605 Alta Vista, Ottawa, Ontario, K1V 7T3",2605 Alta Vista,Ottawa,Ontario,K1V 7T3
Y,OCDSB,Elementary,Churchill Alternative School,"345 Ravenhill, Ottawa, Ontario, K2A 0J5",345 Ravenhill,Ottawa,Ontario,K2A 0J5
Y,OCDSB,Elementary,Clifford Bowey Public School,"1300 Kitchener, Ottawa, Ontario, K1V 6W2",1300 Kitchener,Ottawa,Ontario,K1V 6W2
Y,OCDSB,Elementary,Connaught Public School,"1149 Gladstone, Ottawa, Ontario, K1Y 3H7",1149 Gladstone,Ottawa,Ontario,K1Y 3H7
Y,OCDSB,Elementary,Convent Glen Elementary School,"1708 Grey Nuns, Gloucester, Ontario, K1C 1C1",1708 Grey Nuns,Gloucester,Ontario,K1C 1C1
Y,OCDSB,Elementary,Crystal Bay Centre for Special,"31 Moodie, Nepean, Ontario, K2H 8G1",31 Moodie,Nepean,Ontario,K2H 8G1
Y,OCDSB,Intermediate,D. Aubrey Moodie Intermediate School,"595 Moodie, Nepean, Ontario, K2H 8A8",595 Moodie,Nepean,Ontario,K2H 8A8
Y,OCDSB,Elementary,D. Roy Kennedy Public School,"919 Woodroffe, Ottawa, Ontario, K2A 3G9",919 Woodroffe,Ottawa,Ontario,K2A 3G9
Y,OCDSB,Elementary,Devonshire Community Public School,"100 Breezehill, Ottawa, Ontario, K1Y 2H5",100 Breezehill,Ottawa,Ontario,K1Y 2H5
Y,OCDSB,Elementary,Dunlop Public School,"1310 Pebble, Ottawa, Ontario, K1V 7R8",1310 Pebble,Ottawa,Ontario,K1V 7R8
Y,OCDSB,Elementary,Dunning-Foubert Elementary School,"1610 Prestwick, Orleans, Ontario, K1E 2N1",1610 Prestwick,Orleans,Ontario,K1E 2N1
Y,OCDSB,Intermediate,Earl of March Intermediate School,"4 The Parkway, Kanata, Ontario, K2K 1Y4",4 The Parkway,Kanata,Ontario,K2K 1Y4
Y,OCDSB,Elementary,Elgin Street Public School,"310 Elgin, Ottawa, Ontario, K2P 1M4",310 Elgin,Ottawa,Ontario,K2P 1M4
Y,OCDSB,Elementary,Elizabeth Park Public School,"15 De Niverville, Gloucester, Ontario, K1V 7N9",15 De Niverville,Gloucester,Ontario,K1V 7N9
Y,OCDSB,Elementary,Elmdale Public School,"49 Iona, Ottawa, Ontario, K1Y 3L9",49 Iona,Ottawa,Ontario,K1Y 3L9
Y,OCDSB,Intermediate,Emily Carr Middle School,"2681 Innes, Gloucester, Ontario, K1B 3J7",2681 Innes,Gloucester,Ontario,K1B 3J7
Y,OCDSB,Elementary,Fallingbrook Community Elementary School,"679 Deancourt, Orleans, Ontario, K4A 3E1",679 Deancourt,Orleans,Ontario,K4A 3E1
Y,OCDSB,Elementary,Farley Mowat Public School,"75 Waterbridge, Nepean, Ontario, K2G 6T3",75 Waterbridge,Nepean,Ontario,K2G 6T3
Y,OCDSB,Elementary,Featherston Drive Public School,"1801 Featherston, Ottawa, Ontario, K1H 6P4",1801 Featherston,Ottawa,Ontario,K1H 6P4
Y,OCDSB,Elementary,Fielding Drive Public School,"777 Fielding, Ottawa, Ontario, K1V 7G1",777 Fielding,Ottawa,Ontario,K1V 7G1
Y,OCDSB,Elementary,First Avenue Public School,"73 First, Ottawa, Ontario, K1S 2G1",73 First,Ottawa,Ontario,K1S 2G1
Y,OCDSB,Elementary,Fisher Park/Summit AS Public School,"250 Holland, Ottawa, Ontario, K1Y 0Y5",250 Holland,Ottawa,Ontario,K1Y 0Y5
Y,OCDSB,Elementary,Forest Valley Elementary School,"1570 Forest Valley, Orleans, Ontario, K1C 6X7",1570 Forest Valley,Orleans,Ontario,K1C 6X7
Y,OCDSB,Elementary,General Vanier Public School,"1025 Harkness, Ottawa, Ontario, K1V 6N9",1025 Harkness,Ottawa,Ontario,K1V 6N9
Y,OCDSB,Elementary,Glashan Public School,"28 Arlington, Ottawa, Ontario, K2P 1C2",28 Arlington,Ottawa,Ontario,K2P 1C2
Y,OCDSB,Elementary,Glen Cairn Public School,"182 Morrena, Kanata, Ontario, K2L 1E1",182 Morrena,Kanata,Ontario,K2L 1E1
Y,OCDSB,Elementary,Glen Ogilvie Public School,"46 Centrepark, Gloucester, Ontario, K1B 3C1",46 Centrepark,Gloucester,Ontario,K1B 3C1
Y,OCDSB,Intermediate,Goulbourn Middle School,"2176 Huntley, Stittsville, Ontario, K2S 1B8",2176 Huntley,Stittsville,Ontario,K2S 1B8
Y,OCDSB,Elementary,Grant Alternative School,"2625 Draper, Ottawa, Ontario, K2H 7A1",2625 Draper,Ottawa,Ontario,K2H 7A1
Y,OCDSB,Elementary,Greely Elementary School,"7066 Parkway, Greely, Ontario, K4P 1A9",7066 Parkway,Greely,Ontario,K4P 1A9
Y,OCDSB,Intermediate,Greenbank Middle School,"168 Greenbank, Ottawa, Ontario, K2H 5V2",168 Greenbank,Ottawa,Ontario,K2H 5V2
Y,OCDSB,Elementary,Half Moon Bay Elementary School,"3525 River Run Avenue, Nepean, Ontario, K2J 6E2",3525 River Run Avenue,Nepean,Ontario,K2J 6E2
Y,OCDSB,Elementary,Hawthorne Public School,"2158 St. Laurent, Ottawa, Ontario, K1G 1A9",2158 St. Laurent,Ottawa,Ontario,K1G 1A9
Y,OCDSB,Elementary,Henry Larsen Elementary School,"1750 Sunview, Gloucester, Ontario, K1C 5B3",1750 Sunview,Gloucester,Ontario,K1C 5B3
Y,OCDSB,Intermediate,Henry Munro Middle School,"2105 Kender, Gloucester, Ontario, K1J 6J7",2105 Kender,Gloucester,Ontario,K1J 6J7
Y,OCDSB,Elementary,Heritage Public School,"1375 Colonial, Navan, Ontario, K4B 1N1",1375 Colonial,Navan,Ontario,K4B 1N1
Y,OCDSB,Elementary,Hilson Avenue Public School,"407 Hilson, Ottawa, Ontario, K1Z 6B9",407 Hilson,Ottawa,Ontario,K1Z 6B9
Y,OCDSB,Elementary,Hopewell Avenue Public School,"17 Hopewell, Ottawa, Ontario, K1S 2Y7",17 Hopewell,Ottawa,Ontario,K1S 2Y7
Y,OCDSB,Elementary,Huntley Centennial Public School,"P.O. Box 100, 118 Langstaff, Carp, Ontario, K0A 1L0",118 Langstaff,Carp,Ontario,K0A 1L0
Y,OCDSB,Elementary,J.H. Putman Public School,"2051 Bel-Air, Ottawa, Ontario, K2C 0X2",2051 Bel-Air,Ottawa,Ontario,K2C 0X2
Y,OCDSB,Elementary,Jack Donohue Public School,"101 Penrith, Kanata, Ontario, K2W 1H4",101 Penrith,Kanata,Ontario,K2W 1H4
Y,OCDSB,Elementary,Jockvale Elementary School,"101 Malvern, Nepean, Ontario, K2J 2S8",101 Malvern,Nepean,Ontario,K2J 2S8
Y,OCDSB,Elementary,John Young Elementa$ry School,"5 Morton, Kanata, Ontario, K2L 1W7",5 Morton,Kanata,Ontario,K2L 1W7
Y,OCDSB,Elementary,Kanata North Elementary School,"425 Terry Fox Drive, Kanata, Ontario, K2K 3A2",425 Terry Fox Drive,Kanata,Ontario,K2K 3A2
Y,OCDSB,K-8,Kars on the Rideau Public School,"Box 100, 6680 Dorack, Kars, Ontario, K0A 2E0",6680 Dorack,Kars,Ontario,K0A 2E0
Y,OCDSB,Elementary,Katimavik Elementary School,"64 Chimo, Kanata, Ontario, K2L 1Y9",64 Chimo,Kanata,Ontario,K2L 1Y9
Y,OCDSB,Elementary,Knoxdale Public School,"170 Greenbank, Nepean, Ontario, K2H 5V2",170 Greenbank,Nepean,Ontario,K2H 5V2
Y,OCDSB,Elementary,Lady Evelyn Alternative School,"63 Evelyn, Ottawa, Ontario, K1S 0C6",63 Evelyn,Ottawa,Ontario,K1S 0C6
Y,OCDSB,Elementary,Lakeview Public School,"35 Corkstown, Nepean, Ontario, K2H 7V4",35 Corkstown,Nepean,Ontario,K2H 7V4
Y,OCDSB,Elementary,Le Phare Elementary School,"1965 Naskapi, Gloucester, Ontario, K1J 8M9",1965 Naskapi,Gloucester,Ontario,K1J 8M9
Y,OCDSB,Elementary,Leslie Park Public School,"20 Harrison, Nepean, Ontario, K2H 7N5",20 Harrison,Nepean,Ontario,K2H 7N5
Y,OCDSB,Intermediate,Longfields Davidson Heights Intermediate School,"149 Berrigan, Nepean, Ontario, K2J 5C6",149 Berrigan,Nepean,Ontario,K2J 5C6
Y,OCDSB,Elementary,Manor Park Public School,"100 Braemar, Ottawa, Ontario, K1K 3C9",100 Braemar,Ottawa,Ontario,K1K 3C9
Y,OCDSB,Elementary,Manordale Public School,"16 Carola, Nepean, Ontario, K2G 0Y1",16 Carola,Nepean,Ontario,K2G 0Y1
Y,OCDSB,Elementary,Manotick Public School,"1075 Bridge, Manotick, Ontario, K4M 1H3",1075 Bridge,Manotick,Ontario,K4M 1H3
Y,OCDSB,Elementary,Maple Ridge Elementary School,"1000 Valin, Orleans, Ontario, K4A 4B5",1000 Valin,Orleans,Ontario,K4A 4B5
Y,OCDSB,Elementary,Mary Honeywell Elementary School,"54 Kennevale, Nepean, Ontario, K2J 3B2",54 Kennevale,Nepean,Ontario,K2J 3B2
Y,OCDSB,Elementary,Meadowlands Public School,"10 Fieldrow, Nepean, Ontario, K2G 2Y7",10 Fieldrow,Nepean,Ontario,K2G 2Y7
Y,OCDSB,Elementary,Metcalfe Public School,"2701 8th Line, Metcalfe, Ontario, K0A 2P0",2701 8th Line,Metcalfe,Ontario,K0A 2P0
Y,OCDSB,Elementary,Mutchmor Public School,"185 Fifth, Ottawa, Ontario, K1S 2N1",185 Fifth,Ottawa,Ontario,K1S 2N1
Y,OCDSB,Elementary,North Gower/Marlborough Public School,"Box 130, 2403 Church, North Gower, Ontario, K0A 2T0",Box 130,North Gower,Ontario,K0A 2T0
Y,OCDSB,Elementary,Orleans Wood Elementary School,"7859 Decarie, Gloucester, Ontario, K1C 2J4",7859 Decarie,Gloucester,Ontario,K1C 2J4
Y,OCDSB,Elementary,Osgoode Public School,"Box 70, 5590 Osgoode Main, Osgoode, Ontario, K0A 2W0","5590 Osgoode Main, Osgoode, Ontario, K0A 2W0",Osgoode,Ontario,K0A 2W0
Y,OCDSB,Elementary,Pinecrest Public School,"1281 McWatters, Ottawa, Ontario, K2C 3E7",1281 McWatters,Ottawa,Ontario,K2C 3E7
Y,OCDSB,Elementary,Pleasant Park Public School,"564 Pleasant Park, Ottawa, Ontario, K1H 5N1",564 Pleasant Park,Ottawa,Ontario,K1H 5N1
Y,OCDSB,Elementary,Queen Elizabeth Public School,"689 St Laurent, Ottawa, Ontario, K1K 3A6",689 St Laurent,Ottawa,Ontario,K1K 3A6
Y,OCDSB,Elementary,Queen Mary Street Public School,"557 Queen Mary, Ottawa, Ontario, K1K 1V9",557 Queen Mary,Ottawa,Ontario,K1K 1V9
Y,OCDSB,Elementary,Regina Street Public School,"2599 Regina, Ottawa, Ontario, K2B 8B6",2599 Regina,Ottawa,Ontario,K2B 8B6
Y,OCDSB,Elementary,Richmond Public School,"Box 189, 3499 McBean, Richmond, Ontario, K0A 2Z0",3499 McBean,Richmond,Ontario,K0A 2Z0
Y,OCDSB,Elementary,Riverview Alternative School,"260 Knox, Ottawa, Ontario, K1G 0K8",260 Knox,Ottawa,Ontario,K1G 0K8
Y,OCDSB,Elementary,Robert Bateman Public School,"1250 Blohm, Ottawa, Ontario, K1G 5R8",1250 Blohm,Ottawa,Ontario,K1G 5R8
Y,OCDSB,Elementary,Robert E. Wilson Public School,"373 McArthur, Vanier, Ontario, K1L 6N5",373 McArthur,Vanier,Ontario,K1L 6N5
Y,OCDSB,Elementary,Robert Hopkins Public School,"2011 Glenfern, Gloucester, Ontario, K1J 6H2",2011 Glenfern,Gloucester,Ontario,K1J 6H2
Y,OCDSB,Elementary,Roberta Bondar Public School,"159 Lorry Greenberg, Ottawa, Ontario, K1T 3J6",159 Lorry Greenberg,Ottawa,Ontario,K1T 3J6
Y,OCDSB,Elementary,Roch Carrier Elementary School,"401 Stonehaven, Kanata, Ontario, K2M 3B5",401 Stonehaven,Kanata,Ontario,K2M 3B5
Y,OCDSB,Elementary,Rockcliffe Park Public School,"350 Buena Vista, Ottawa, Ontario, K1M 1C1",350 Buena Vista,Ottawa,Ontario,K1M 1C1
Y,OCDSB,Elementary,Roland Michener Public School,"100 Penfield, Kanata, Ontario, K2K 1M2",100 Penfield,Kanata,Ontario,K2K 1M2
Y,OCDSB,Elementary,Sawmill Creek Elementary School,"3400 D'Aoust, Gloucester, Ontario, K1T 1R5",3400 D'Aoust,Gloucester,Ontario,K1T 1R5
Y,OCDSB,Elementary,Severn Avenue Public School,"2553 Severn, Ottawa, Ontario, K2B 7V8",2553 Severn,Ottawa,Ontario,K2B 7V8
Y,OCDSB,Elementary,Sir Winston Churchill Public School,"49 Mulvagh, Nepean, Ontario, K2E 6M7",49 Mulvagh,Nepean,Ontario,K2E 6M7
Y,OCDSB,Elementary,South March Public School,"1032 Klondike, Kanata, Ontario, K2K 0H9",1032 Klondike,Kanata,Ontario,K2K 0H9
Y,OCDSB,Elementary,Stephen Leacock Public School,"25 Leacock, Kanata, Ontario, K2K 1S2",25 Leacock,Kanata,Ontario,K2K 1S2
Y,OCDSB,Elementary,Steve MacLean Public School,"4175 Spratt, Gloucester, Ontario, K1V 1T6",4175 Spratt,Gloucester,Ontario,K1V 1T6
Y,OCDSB,Elementary,Stittsville Public School,"40 Granite Ridge, Stittsville, Ontario, K2S 1Y9",40 Granite Ridge,Stittsville,Ontario,K2S 1Y9
Y,OCDSB,Elementary,Stonecrest Elementary School,"3791 Stonecrest, Woodlawn, Ontario, K0A 3M0",3791 Stonecrest,Woodlawn,Ontario,K0A 3M0
Y,OCDSB,Elementary,Terry Fox Elementary School,"6400 Jeanne D'Arc, Orleans, Ontario, K1C 2S7",6400 Jeanne D'Arc,Orleans,Ontario,K1C 2S7
Y,OCDSB,Elementary,Trillium Elementary School,"1515 Varennes, Orleans, Ontario, K4A 3S1",1515 Varennes,Orleans,Ontario,K4A 3S1
Y,OCDSB,Elementary,Vincent Massey Public School,"745 Smyth, Ottawa, Ontario, K1G 1N9",745 Smyth,Ottawa,Ontario,K1G 1N9
Y,OCDSB,Elementary,Viscount Alexander Public School,"55 Mann, Ottawa, Ontario, K1N 6Y7",55 Mann,Ottawa,Ontario,K1N 6Y7
Y,OCDSB,Elementary,W. Erskine Johnston Public School,"50 Varley, Kanata, Ontario, K2K 1G7",50 Varley,Kanata,Ontario,K2K 1G7
Y,OCDSB,Elementary,W.E. Gowling Public School,"250 Anna, Ottawa, Ontario, K1Z 7V6",250 Anna,Ottawa,Ontario,K1Z 7V6
Y,OCDSB,Elementary,W.O. Mitchell Elementary School,"80 Steeple Chase, Kanata, Ontario, K2M 2A6",80 Steeple Chase,Kanata,Ontario,K2M 2A6
Y,OCDSB,Elementary,Westwind Public School,"111 Hartsmere, STITTSVILLE, Ontario, K2S 2G1",111 Hartsmere,STITTSVILLE,Ontario,K2S 2G1
Y,OCDSB,Elementary,Woodroffe Avenue Public School,"235 Woodroffe, Ottawa, Ontario, K2A 3V3",235 Woodroffe,Ottawa,Ontario,K2A 3V3
Y,OCDSB,Elementary,York Street Public School,"310 York, Ottawa, Ontario, K1N 5V3",310 York,Ottawa,Ontario,K1N 5V3
Y,CECCE,Intermediate,École intermédiaire catholique Académie catholique Ange-Gabriel,"1515 Kensington, Brockville, Ontario, K6V 6H9",1515 Kensington,Brockville,Ontario,K6V 6H9
Y,CECCE,Intermediate,École intermédiaire catholique Béatrice-Desloges,"1999 Provence, Orléans, Ontario, K4A 3Y6",1999 Provence,Orléans,Ontario,K4A 3Y6
Y,CECCE,Intermediate,École intermédiaire catholique Franco-Cité,"623 Smyth, Ottawa, Ontario, K1G 1N7",623 Smyth,Ottawa,Ontario,K1G 1N7
Y,CECCE,Intermediate,École intermédiaire catholique Franco-Ouest,"411 Seyton, Nepean, Ontario, K2H 8X1",411 Seyton,Nepean,Ontario,K2H 8X1
Y,CECCE,Intermediate,École intermédiaire catholique Garneau,"6588 Carrière, Orleans, Ontario, K1C 1J4",6588 Carrière,Orleans,Ontario,K1C 1J4
Y,CECCE,Intermediate,École intermédiaire catholique Jeanne-Lajoie,"255 Pembroke Ouest, Pembroke, Ontario, K8A 5R3",255 Pembroke Ouest,Pembroke,Ontario,K8A 5R3
Y,CECCE,Intermediate,École intermédiaire catholique Marie-Rivier,"711 Dalton, Kingston, Ontario, K7M 8N6",711 Dalton,Kingston,Ontario,K7M 8N6
Y,CECCE,Intermediate,École intermédiaire catholique Orléans,"6401 Renaud, Orléans, Ontario, K1W 0H8",6401 Renaud,Orléans,Ontario,K1W 0H8
Y,CECCE,Intermediate,École intermédiaire catholique Paul-Desmarais,"5315 Abbott, Ottawa, Ontario, K2S 0X3",5315 Abbott,Ottawa,Ontario,K2S 0X3
Y,CECCE,Intermediate,École intermédiaire catholique Pavillon intermédiaire de Kemptville,"K0G 1J1, 830 Prescott, Kemptville, Ontario, K0G 1J0",830 Prescott,Kemptville,Ontario,K0G 1J0
Y,CECCE,Intermediate,École intermédiaire catholique Pierre-Savard,"1110 Longfields, Ottawa, Ontario, K2J 0H9",1110 Longfields,Ottawa,Ontario,K2J 0H9
Y,CECCE,Intermediate,École intermédiaire catholique Samuel-Genest,"704 Carson, Ottawa, Ontario, K1K 2H3",704 Carson,Ottawa,Ontario,K1K 2H3
Y,CECCE,Elementary,École élémentaire Académie catholique Ange-Gabriel,"1515 Kensington, Brockville, Ontario, K6V 6H9",1515 Kensington,Brockville,Ontario,K6V 6H9
Y,CECCE,Elementary,École élémentaire catholique Alain-Fortin,"676 Lakeridge, Orléans, Ontario, K4A 0J8",676 Lakeridge,Orléans,Ontario,K4A 0J8
Y,CECCE,Elementary,École élémentaire catholique Arc-en-ciel,"1830 Portobello, Orléans, Ontario, K4A 3T6",1830 Portobello,Orléans,Ontario,K4A 3T6
Y,CECCE,Elementary,École élémentaire catholique Avalon 2,"665, Promenade des Aubépines, Orléans, Ontario, K4A 0Z3",665 Promenade des Aubépines,Orléans,Ontario,K4A 0Z3
Y,CECCE,Elementary,École élémentaire catholique Bernard-Grandmaître,"4170 Spratt, Ottawa, Ontario, K1V 0Z5",4170 Spratt,Ottawa,Ontario,K1V 0Z5
Y,CECCE,Elementary,École élémentaire catholique De la Découverte,"866 Scala, Cumberland, Ontario, K4A 4T6",866 Scala,Cumberland,Ontario,K4A 4T6
Y,CECCE,Elementary,École élémentaire catholique Des Voyageurs,"6030 Voyageur, Orléans, Ontario, K1C 2T1",6030 Voyageur,Orléans,Ontario,K1C 2T1
Y,CECCE,Elementary,École élémentaire catholique Des Pins,"1487 Ridgebrook, Gloucester, Ontario, K1B 4K6",1487 Ridgebrook,Gloucester,Ontario,K1B 4K6
Y,CECCE,Elementary,École élémentaire catholique Des Pionniers,"720 Merkley, Orléans, Ontario, K4A 1L8",720 Merkley,Orléans,Ontario,K4A 1L8
Y,CECCE,Elementary,École élémentaire catholique Elisabeth-Bruyère,"100 Stonehaven, Kanata, Ontario, K2M 2H4",100 Stonehaven,Kanata,Ontario,K2M 2H4
Y,CECCE,Elementary,École élémentaire catholique George-Étienne-Cartier,"880 Thorndale, Ottawa, Ontario, K1V 6Y3",880 Thorndale,Ottawa,Ontario,K1V 6Y3
Y,CECCE,Elementary,École élémentaire catholique Horizon-Jeunesse,"349 Olmstead, Vanier, Ontario, K1L 1B1",349 Olmstead,Vanier,Ontario,K1L 1B1
Y,CECCE,Elementary,École élémentaire catholique J.-L.-Couroux,"10 Findlay, Carleton Place, Ontario, K7C 4K1",10 Findlay,Carleton Place,Ontario,K7C 4K1
Y,CECCE,Elementary,École élémentaire catholique Jean-Robert-Gauthier,"651 Chapman Mills, Ottawa, Ontario, K2J 0W7",651 Chapman Mills,Ottawa,Ontario,K2J 0W7
Y,CECCE,Elementary,École élémentaire catholique Jeanne-Lajoie,"1255 Pembroke Ouest, Pembroke, Ontario, K8A 5R3",1255 Pembroke Ouest,Pembroke,Ontario,K8A 5R3
Y,CECCE,Elementary,École élémentaire catholique L'Envol,"45 Johnson, Trenton, Ontario, K8V 6V7",45 Johnson,Trenton,Ontario,K8V 6V7
Y,CECCE,Elementary,École élémentaire catholique L'Étoile-de-l'Est,"6220 Beauséjour, Orléans, Ontario, K1C 8E4",6220 Beauséjour,Orléans,Ontario,K1C 8E4
Y,CECCE,Elementary,École élémentaire catholique La Vérendrye,"614 Eastvale, Gloucester, Ontario, K1J 6Z6",614 Eastvale,Gloucester,Ontario,K1J 6Z6
Y,CECCE,Elementary,École élémentaire catholique Laurier-Carrière,"14 Four Seasons, Nepean, Ontario, K2E 7P8",14 Four Seasons,Nepean,Ontario,K2E 7P8
Y,CECCE,Elementary,École élémentaire catholique Marius-Barbeau,"1345 Nottinghill, Ottawa, Ontario, K1V 6T3",1345 Nottinghill,Ottawa,Ontario,K1V 6T3
Y,CECCE,Elementary,École élémentaire catholique Mgr-Rémi-Gaulin,"51 Virginia, Kingston, Ontario, K7K 5Y3",51 Virginia,Kingston,Ontario,K7K 5Y3
Y,CECCE,Elementary,École élémentaire catholique Montfort,"350 Den Haag, Ottawa, Ontario, K1K 0W9",350 Den Haag,Ottawa,Ontario,K1K 0W9
Y,CECCE,Elementary,École élémentaire catholique Notre-Dame-des-Champs,"6280 Renaud, Ottawa, Ontario, K4B 1H9",6280 Renaud,Ottawa,Ontario,K4B 1H9
Y,CECCE,Elementary,École élémentaire catholique Pierre-Elliott-Trudeau,"601 Longfields, Nepean, Ontario, K2J 4X1",601 Longfields,Nepean,Ontario,K2J 4X1
Y,CECCE,Elementary,École élémentaire catholique Reine-des-Bois,"1450 Duford, Orléans, Ontario, K1E 1E6",1450 Duford,Orléans,Ontario,K1E 1E6
Y,CECCE,Elementary,École élémentaire catholique Roger-Saint-Denis,"186 Barrow, Kanata, Ontario, K2L 2C7",186 Barrow,Kanata,Ontario,K2L 2C7
Y,CECCE,Elementary,École élémentaire catholique Saint-François-d'Assise,"35 Melrose, Ottawa, Ontario, K1Y 1T8",35 Melrose,Ottawa,Ontario,K1Y 1T8
Y,CECCE,Elementary,École élémentaire catholique Saint-Guillaume,"5750 Buckland, Vars, Ontario, K0A 3H0",5750 Buckland,Vars,Ontario,K0A 3H0
Y,CECCE,Elementary,École élémentaire catholique Saint-Jean-Paul II,"5473 Abbott Est, Stittsville, Ontario, K2S 0A8",5473 Abbott Est,Stittsville,Ontario,K2S 0A8
Y,CECCE,Elementary,École élémentaire catholique Saint-Joseph d'Orléans,"6664 Carrière, Orléans, Ontario, K1C 1J4",6664 Carrière,Orléans,Ontario,K1C 1J4
Y,CECCE,Elementary,École élémentaire catholique Saint-Rémi,"100 promenade Walden, KANATA, Ontario, K2K 0G8",100 promenade Walden,KANATA,Ontario,K2K 0G8
Y,CECCE,Elementary,École élémentaire catholique Sainte-Anne,"235 Beausoleil, Ottawa, Ontario, K1N 8X8",235 Beausoleil,Ottawa,Ontario,K1N 8X8
Y,CECCE,Elementary,École élémentaire catholique Sainte-Bernadette,"3781 Sixth, Gloucester, Ontario, K1T 1K5",3781 Sixth,Gloucester,Ontario,K1T 1K5
Y,CECCE,Elementary,École élémentaire catholique Sainte-Geneviève,"2198 Arch, Ottawa, Ontario, K1G 2H7",2198 Arch,Ottawa,Ontario,K1G 2H7
Y,CECCE,Elementary,École élémentaire catholique Sainte-Kateri,"2450 River Mist, Ottawa, Ontario, K2J 0S2",2450 River Mist,Ottawa,Ontario,K2J 0S2
Y,CECCE,Elementary,École élémentaire catholique Sainte-Marguerite-Bourgeoys,"C.P. 539, 306 Read, Merrickville, Ontario, K0G 1N0",306 Read,Merrickville,Ontario,K0G 1N0
Y,CECCE,Elementary,École élémentaire catholique Sainte-Marie,"2599 Innes, Gloucester, Ontario, K1B 3J8",2599 Innes,Gloucester,Ontario,K1B 3J8
Y,CECCE,Elementary,École élémentaire catholique Sainte-Thérèse-d'Avila,"CP 1095, 9575 Marionville, Marionville, Ontario, K4R 1E5",9575 Marionville,Marionville,Ontario,K4R 1E5
Y,CECCE,Elementary,École élémentaire catholique Terre-des-Jeunes,"1303 Fellows, Ottawa, Ontario, K2C 2V8",1303 Fellows,Ottawa,Ontario,K2C 2V8
Y,CECCE,Elementary,École élémentaire catholique d'enseignement personnalisé La Source,"1445 Duford, Orléans, Ontario, K1E 1E8",1445 Duford,Orléans,Ontario,K1E 1E8
Y,CECCE,Elementary,École élémentaire catholique d'enseignement personnalisé Lamoureux,"2540 Kaladar, Ottawa, Ontario, K1V 8C5",2540 Kaladar,Ottawa,Ontario,K1V 8C5
Y,CECCE,Elementary,École élémentaire catholique d'enseignement personnalisé Édouard-Bond,"920 Parkhaven, Ottawa, Ontario, K2B 5K3",920 Parkhaven,Ottawa,Ontario,K2B 5K3
Y,OCSB,Intermediate,All Saints Catholic Intermediate School,"5115 Kanata, Kanata, Ontario, K2K 3K5",5115 Kanata,Kanata,Ontario,K2K 3K5
Y,OCSB,Elementary,Assumption Catholic Elementary School,"236 Levis, Vanier, Ontario, K1L 6H8",236 Levis,Vanier,Ontario,K1L 6H8
Y,OCSB,Elementary,Chapel Hill Catholic Elementary School,"1534 Forest Valley, Orleans, Ontario, K1C 6G9",1534 Forest Valley,Orleans,Ontario,K1C 6G9
Y,OCSB,Elementary,Convent Glen Catholic Elementary School,"6212 Jeanne d'Arc, Orleans, Ontario, K1C 2M4",6212 Jeanne d'Arc,Orleans,Ontario,K1C 2M4
Y,OCSB,Elementary,Corpus Christi Catholic Elementary School,"157 Fourth, Ottawa, Ontario, K1S 2L5",157 Fourth,Ottawa,Ontario,K1S 2L5
Y,OCSB,Elementary,Divine Infant Catholic Elementary School,"8100 Jeanne d'Arc, Orleans, Ontario, K1E 2E1",8100 Jeanne d'Arc,Orleans,Ontario,K1E 2E1
Y,OCSB,Elementary,Dr F J McDonald Catholic Elementary School,"2860 Ahearn, Ottawa, Ontario, K2B 6Z9",2860 Ahearn,Ottawa,Ontario,K2B 6Z9
Y,OCSB,Elementary,Frank Ryan Catholic Senior Elementary School,"128 Chesterton, Nepean, Ontario, K2E 5T8",128 Chesterton,Nepean,Ontario,K2E 5T8
Y,OCSB,Elementary,Georges Vanier Catholic Elementary School,"40 Varley, Kanata, Ontario, K2K 1G5",40 Varley,Kanata,Ontario,K2K 1G5
Y,OCSB,Elementary,Good Shepherd Elementary School,"101 Bearbrook, Gloucester, Ontario, K1B 3H5",101 Bearbrook,Gloucester,Ontario,K1B 3H5
Y,OCSB,Elementary,Guardian Angels Elementary School,"4 Baywood, Stittsville, Ontario, K2S 1K5",4 Baywood,Stittsville,Ontario,K2S 1K5
Y,OCSB,Elementary,Half Moon Bay CES (Barrhaven South) Catholic School,"2525 River Mist, NEPEAN, Ontario, K2J 5Z1",2525 River Mist,NEPEAN,Ontario,K2J 5Z1
Y,OCSB,Elementary,Holy Cross Elementary School,"2820 Springland, Ottawa, Ontario, K1V 6M4",2820 Springland,Ottawa,Ontario,K1V 6M4
Y,OCSB,Elementary,Holy Family Elementary School,"245 Owl, Ottawa, Ontario, K1V 9K3",245 Owl,Ottawa,Ontario,K1V 9K3
Y,OCSB,Elementary,Holy Redeemer Elementary School,"75 McCurdy, Kanata, Ontario, K2L 3W6",75 McCurdy,Kanata,Ontario,K2L 3W6
Y,OCSB,Elementary,Holy Spirit Elementary School,"Box 489, 1383 Main, Stittsville, Ontario, K2S 1A6",1383 Main,Stittsville,Ontario,K2S 1A6
Y,OCSB,Intermediate,Holy Trinity Catholic Intermediate School,"180 Katimavik, Kanata, Ontario, K2L 4A7",180 Katimavik,Kanata,Ontario,K2L 4A7
Y,OCSB,Intermediate,Immaculata Intermediate School,"140 Main, Ottawa, Ontario, K1S 5P4",140 Main,Ottawa,Ontario,K1S 5P4
Y,OCSB,Elementary,John Paul II Elementary School,"1500 Beaverpond, Gloucester, Ontario, K1B 3R9",1500 Beaverpond,Gloucester,Ontario,K1B 3R9
Y,OCSB,Intermediate,Lester B. Pearson Catholic Intermediate School,"2072 Jasmine, Gloucester, Ontario, K1J 8M5",2072 Jasmine,Gloucester,Ontario,K1J 8M5
Y,OCSB,Elementary,Monsignor Paul Baxter Elementary School,"333 Beatrice, Nepean, Ontario, K2J 4W1",333 Beatrice,Nepean,Ontario,K2J 4W1
Y,OCSB,Intermediate,Mother Teresa Catholic Intermediate School,"440 Longfields, Nepean, Ontario, K2J 4T1",440 Longfields,Nepean,Ontario,K2J 4T1
Y,OCSB,Intermediate,Notre Dame Intermediate School,"710 Broadview, Ottawa, Ontario, K2A 2M2",710 Broadview,Ottawa,Ontario,K2A 2M2
Y,OCSB,Elementary,Our Lady of Fatima Elementary School,"2135 Knightsbridge, Ottawa, Ontario, K2A 0R3",2135 Knightsbridge,Ottawa,Ontario,K2A 0R3
Y,OCSB,Elementary,Our Lady of Mount Carmel Elementary School,"675 Gardenvale, Ottawa, Ontario, K1K 1C9",675 Gardenvale,Ottawa,Ontario,K1K 1C9
Y,OCSB,Elementary,Our Lady of Peace Elementary School,"3877 Richmond, Nepean, Ontario, K2H 5C1",3877 Richmond,Nepean,Ontario,K2H 5C1
Y,OCSB,Elementary,Our Lady of Victory Elementary School,"1175 Soderlind, Ottawa, Ontario, K2C 3B3",1175 Soderlind,Ottawa,Ontario,K2C 3B3
Y,OCSB,Elementary,Our Lady of Wisdom Elementary School,"1565 St Georges, Orleans, Ontario, K1E 1R2",1565 St Georges,Orleans,Ontario,K1E 1R2
Y,OCSB,Elementary,Prince of Peace Elementary School,"1620 Heatherington, Ottawa, Ontario, K1V 9P5",1620 Heatherington,Ottawa,Ontario,K1V 9P5
Y,OCSB,Intermediate,Sacred Heart Intermediate School,"5870 Abbott, Stittsville, Ontario, K2S 1X4",5870 Abbott,Stittsville,Ontario,K2S 1X4
Y,OCSB,Elementary,St Andrew Elementary School,"201 Crestway, Nepean, Ontario, K2G 6Z3",201 Crestway,Nepean,Ontario,K2G 6Z3
Y,OCSB,Elementary,St Anne Elementary School,"500 Stonehaven, Kanata, Ontario, K2M 2V6",500 Stonehaven,Kanata,Ontario,K2M 2V6
Y,OCSB,Elementary,St Anthony Elementary School,"391 Booth, Ottawa, Ontario, K1R 7K5",391 Booth,Ottawa,Ontario,K1R 7K5
Y,OCSB,Elementary,St Augustine Elementary School,"1009 Arnot, Ottawa, Ontario, K2C 0H5",1009 Arnot,Ottawa,Ontario,K2C 0H5
Y,OCSB,Elementary,St Bernard Elementary School,"1722 St Bernard, Ottawa, Ontario, K1T 1K8",1722 St Bernard,Ottawa,Ontario,K1T 1K8
Y,OCSB,Elementary,St Brigid Elementary School,"200 Springfield, Ottawa, Ontario, K1M 1C2",200 Springfield,Ottawa,Ontario,K1M 1C2
Y,OCSB,Elementary,St Catherine Elementary School,"2717 Albert, Metcalfe, Ontario, K0A 2P0",2717 Albert,Metcalfe,Ontario,K0A 2P0
Y,OCSB,Elementary,St Clare Elementary School,"2133 Gardenway, Orleans, Ontario, K4A 3M2",2133 Gardenway,Orleans,Ontario,K4A 3M2
Y,OCSB,Elementary,St Daniel Elementary School,"1313 Field, Ottawa, Ontario, K2C 2P9",1313 Field,Ottawa,Ontario,K2C 2P9
Y,OCSB,Elementary,St Elizabeth Ann Seton Elementary School,"41 Weybridge, Nepean, Ontario, K2J 2Z8",41 Weybridge,Nepean,Ontario,K2J 2Z8
Y,OCSB,Elementary,St Elizabeth Elementary School,"1366 Coldrey, Ottawa, Ontario, K1Z 7P5",1366 Coldrey,Ottawa,Ontario,K1Z 7P5
Y,OCSB,Elementary,St Emily (Elementary) Separate School,"500 Chapman Mills, Nepean, Ontario, K2J 0J2",500 Chapman Mills,Nepean,Ontario,K2J 0J2
Y,OCSB,Elementary,St Francis of Assisi Elementary School,"795 Watters, Orleans, Ontario, K4A 2T2",795 Watters,Orleans,Ontario,K4A 2T2
Y,OCSB,Elementary,St George Elementary School,"130 Keyworth, Ottawa, Ontario, K1Y 0E6",130 Keyworth,Ottawa,Ontario,K1Y 0E6
Y,OCSB,Elementary,St Gregory Elementary School,"148 Meadowlands, Nepean, Ontario, K2G 2S5",148 Meadowlands,Nepean,Ontario,K2G 2S5
Y,OCSB,Elementary,St Isidore Elementary School,"1105 March, Kanata, Ontario, K2K 1X7",1105 March,Kanata,Ontario,K2K 1X7
Y,OCSB,Elementary,St James Elementary School,"50 Stonehaven, Kanata, Ontario, K2M 2K6",50 Stonehaven,Kanata,Ontario,K2M 2K6
Y,OCSB,Elementary,St Jerome Elementary School,"4330 Spratt, Gloucester, Ontario, K1V 2A7",4330 Spratt,Gloucester,Ontario,K1V 2A7
Y,OCSB,Elementary,St John the Apostle Elementary School,"30 Costello, Nepean, Ontario, K2H 7C5",30 Costello,Nepean,Ontario,K2H 7C5
Y,OCSB,Intermediate,St Joseph Intermediate School,"3333 Greenbank, Ottawa, Ontario, K2J 4J1",3333 Greenbank,Ottawa,Ontario,K2J 4J1
Y,OCSB,Elementary,St Leonard Elementary School,"5344 Long Island, Manotick, Ontario, K4M 1E8",5344 Long Island,Manotick,Ontario,K4M 1E8
Y,OCSB,Elementary,St Luke (Nepean) Elementary School,"60 Mountshannon, Nepean, Ontario, K2J 4B8",60 Mountshannon,Nepean,Ontario,K2J 4B8
Y,OCSB,Elementary,St Luke (Ottawa) Elementary School,"2485 Dwight, Ottawa, Ontario, K1G 1C7",2485 Dwight,Ottawa,Ontario,K1G 1C7
Y,OCSB,Elementary,St Marguerite d'Youville Elementary School,"89 Lorry Greenberg, Ottawa, Ontario, K1T 3J6",89 Lorry Greenberg,Ottawa,Ontario,K1T 3J6
Y,OCSB,Intermediate,St Mark Intermediate School,"1040 Dozois, Manotick, Ontario, K4M 1B2",1040 Dozois,Manotick,Ontario,K4M 1B2
Y,OCSB,Elementary,St Martin de Porres Elementary School,"20 McKitrick, Kanata, Ontario, K2L 1T7",20 McKitrick,Kanata,Ontario,K2L 1T7
Y,OCSB,Elementary,St Mary (Gloucester) Elementary School,"5536 BANK, GLOUCESTER, Ontario, K1X 1G9",5536 BANK,GLOUCESTER,Ontario,K1X 1G9
Y,OCSB,Intermediate,St Matthew Intermediate School,"6550 Bilberry, Orleans, Ontario, K1C 2S9",6550 Bilberry,Orleans,Ontario,K1C 2S9
Y,OCSB,Elementary,St Michael (Corkery) Elementary School,"1572 Corkery, Carp, Ontario, K0A 1L0",1572 Corkery,Carp,Ontario,K0A 1L0
Y,OCSB,Elementary,St Michael (Fitzroy) Elementary School,"159 Kedey, Fitzroy Harbour, Ontario, K0A 1X0",159 Kedey,Fitzroy Harbour,Ontario,K0A 1X0
Y,OCSB,Elementary,St Michael Elementary School,"437 Donald, Ottawa, Ontario, K1K 1L8",437 Donald,Ottawa,Ontario,K1K 1L8
Y,OCSB,Elementary,St Monica Elementary School,"2000 Merivale, Nepean, Ontario, K2G 1G6",2000 Merivale,Nepean,Ontario,K2G 1G6
Y,OCSB,Elementary,St Patrick Elementary School,"68 Larkin, Nepean, Ontario, K2J 1A9",68 Larkin,Nepean,Ontario,K2J 1A9
Y,OCSB,Elementary,St Patrick's Intermediate School,"1485 Heron, Ottawa, Ontario, K1V 6A6",1485 Heron,Ottawa,Ontario,K1V 6A6
Y,OCSB,Intermediate,St Paul Intermediate School,"2675 Draper, Ottawa, Ontario, K2H 7A1",2675 Draper,Ottawa,Ontario,K2H 7A1
Y,OCSB,Intermediate,St Peter Intermediate School,"750 Charlemagne, Orleans, Ontario, K4A 3M4",750 Charlemagne,Orleans,Ontario,K4A 3M4
Y,OCSB,Elementary,St Philip Elementary School,"Box 129, 79 Maitland, Richmond, Ontario, K0A 2Z0",79 Maitland,Richmond,Ontario,K0A 2Z0
Y,OCSB,Intermediate,St Pius X Intermediate School,"1481 Fisher, Ottawa, Ontario, K2C 1X4",1481 Fisher,Ottawa,Ontario,K2C 1X4
Y,OCSB,Elementary,St Rita Elementary School,"1 Inverness, Nepean, Ontario, K2E 6N6",1 Inverness,Nepean,Ontario,K2E 6N6
Y,OCSB,Elementary,St Theresa Elementary School,"2000 Portobello, Cumberland, Ontario, K4A 4M9",2000 Portobello,Cumberland,Ontario,K4A 4M9
Y,OCSB,Elementary,St Thomas More Elementary School,"1620 Blohm, Ottawa, Ontario, K1G 5N6",1620 Blohm,Ottawa,Ontario,K1G 5N6
Y,OCSB,Elementary,St. Brother Andre Elementary School,"1923 Elmridge, Gloucester, Ontario, K1J 8G7",1923 Elmridge,Gloucester,Ontario,K1J 8G7
Y,OCSB,Elementary,St. Cecilia School Catholic School,"3490 Cambrian, Nepean, Ontario, K2J 0V1",3490 Cambrian,Nepean,Ontario,K2J 0V1
Y,OCSB,Elementary,St. Dominic Catholic Elementary School,"2300 Esprit, Cumberland, Ontario, K4A 0T5",2300 Esprit,Cumberland,Ontario,K4A 0T5
Y,OCSB,Intermediate,St. Francis Xavier (7-8) Catholic School,"3740 Spratt, Gloucester, Ontario, K1V 2M1",3740 Spratt,Gloucester,Ontario,K1V 2M1
Y,OCSB,Elementary,St. Gabriel Elementary School,"400 Keyrock, Kanata, Ontario, K2T 0G6",400 Keyrock,Kanata,Ontario,K2T 0G6
Y,OCSB,Elementary,St. Gemma Elementary School,"1760 McMaster, Ottawa, Ontario, K1H 6R8",1760 McMaster,Ottawa,Ontario,K1H 6R8
Y,OCSB,Elementary,St. John XXIII Elementary School,"165 Knoxdale, Nepean, Ontario, K2G 1B1",165 Knoxdale,Nepean,Ontario,K2G 1B1
Y,OCSB,Elementary,St. Kateri Tekakwitha Elementary School,"6400 Beausejour, Orleans, Ontario, K1C 4W2",6400 Beausejour,Orleans,Ontario,K1C 4W2
Y,OCSB,Elementary,St. Rose of Lima Elementary School,"50 Bayshore, Nepean, Ontario, K2B 6M8",50 Bayshore,Nepean,Ontario,K2B 6M8
Y,OCSB,Elementary,St. Stephen Catholic Elementary School,"1145 Stittsville Main, Stittsville, Ontario, K2S 0M5",1145 Stittsville Main,Stittsville,Ontario,K2S 0M5
Y,OCSB,Elementary,Thomas D'Arcy McGee Catholic Elementary School,"635 Laverendrye, Gloucester, Ontario, K1J 7C2",635 Laverendrye,Gloucester,Ontario,K1J 7C2
Y,OCSB,Elementary,Uplands Catholic Elementary School,"17 de Niverville, Gloucester, Ontario, K1V 7N9",17 de Niverville,Gloucester,Ontario,K1V 7N9
Y,CEPEO,Elementary,École intermédiaire L'Équinoxe,"412 PEMBROKE OUEST, PEMBROKE, Ontario, K8A 5N6",412 PEMBROKE OUEST,PEMBROKE,Ontario,K8A 5N6
Y,CEPEO,Elementary,École élémentaire L'Académie de la Seigneurie,"731 des Pommiers, Casselman, Ontario, K0A 1M0",731 des Pommiers,Casselman,Ontario,K0A 1M0
Y,CEPEO,Elementary,École élémentaire publique Carrefour Jeunesse,"927 St-Jean, Rockland, Ontario, K4K 1P4",927 St-Jean,Rockland,Ontario,K4K 1P4
Y,CEPEO,Elementary,École élémentaire publique Charlotte Lemieux,"2093 Bel-Air, Ottawa, Ontario, K2C 0X2",2093 Bel-Air,Ottawa,Ontario,K2C 0X2
Y,CEPEO,Elementary,École élémentaire publique Cité Jeunesse,"30 Fullerton, Trenton, Ontario, K8V 1E4",30 Fullerton,Trenton,Ontario,K8V 1E4
Y,CEPEO,Elementary,École élémentaire publique De la Rivière Castor,"1229, 100 Maheu, Embrun, Ontario, K0A 1W0",1229 - 100 Maheu,Embrun,Ontario,K0A 1W0
Y,CEPEO,Elementary,École élémentaire publique De la Salle,"501 ancienne St-Patrick, Ottawa, Ontario, K1N 8R3",501 ancienne St-Patrick,Ottawa,Ontario,K1N 8R3
Y,CEPEO,Elementary,École élémentaire publique Des Sentiers,"2159 Nantes, Orléans, Ontario, K4A 4C4",2159 Nantes,Orléans,Ontario,K4A 4C4
Y,CEPEO,Elementary,École élémentaire publique Francojeunesse,"119 Osgoode, Ottawa, Ontario, K1N 6S3",119 Osgoode,Ottawa,Ontario,K1N 6S3
Y,CEPEO,Elementary,École élémentaire publique Gabrielle-Roy,"3395 Daoust, Gloucester, Ontario, K1T 4A8",3395 Daoust,Gloucester,Ontario,K1T 4A8
Y,CEPEO,Elementary,École élémentaire publique Gisèle-Lalonde,"500 Millennium, Orléans, Ontario, K4A 4X3",500 Millennium,Orléans,Ontario,K4A 4X3
Y,CEPEO,Elementary,École élémentaire publique Jeanne-Sauvé,"1917 Gardenway, Orléans, Ontario, K4A 2Y7",1917 Gardenway,Orléans,Ontario,K4A 2Y7
Y,CEPEO,Elementary,École élémentaire publique Kanata,"1385 Halton, Kanata, Ontario, K2K 2P9",1385 Halton,Kanata,Ontario,K2K 2P9
Y,CEPEO,Elementary,École élémentaire publique Kemptville,"Prescott, Kemptville, Ontario, K0G 1J0",Prescott,Kemptville,Ontario,K0G 1J0
Y,CEPEO,Elementary,École élémentaire publique L'Héritage,"1111 Montréal, Cornwall, Ontario, K6H 1E1",1111 Montréal,Cornwall,Ontario,K6H 1E1
Y,CEPEO,Elementary,École élémentaire publique L'Odyssée,"1770 Grey Nuns, Orléans, Ontario, K1C 1C3",1770 Grey Nuns,Orléans,Ontario,K1C 1C3
Y,CEPEO,Elementary,École élémentaire publique L'Équinoxe,"412 PEMBROKE OUEST, PEMBROKE, Ontario, K8A 5N6",412 PEMBROKE OUEST,PEMBROKE,Ontario,K8A 5N6
Y,CEPEO,Elementary,École élémentaire publique Le Prélude,"6025 Longleaf, Orléans, Ontario, K1W 1G3",6025 Longleaf,Orléans,Ontario,K1W 1G3
Y,CEPEO,Elementary,École élémentaire publique Le Sommet,"894 Cécile, Hawkesbury, Ontario, K6A 3R5",894 Cécile,Hawkesbury,Ontario,K6A 3R5
Y,CEPEO,Elementary,École élémentaire publique Le Trillium,"307 Montgomery, Vanier, Ontario, K1L 7X5",307 Montgomery,Vanier,Ontario,K1L 7X5
Y,CEPEO,Elementary,École élémentaire publique Louis-Riel,"1655 Bearbrook, Gloucester, Ontario, K1B 4N3",1655 Bearbrook,Gloucester,Ontario,K1B 4N3
Y,CEPEO,Elementary,École élémentaire publique Madeleine-de-Roybon,"72 Gilmour, Kingston, Ontario, K7M 9G6",72 Gilmour,Kingston,Ontario,K7M 9G6
Y,CEPEO,Elementary,École élémentaire publique Marc-Garneau,"30 Fullerton, Trenton, Ontario, K8V 1E4",30 Fullerton,Trenton,Ontario,K8V 1E4
Y,CEPEO,Elementary,École élémentaire publique Marie-Curie,"860 Colson, Ottawa, Ontario, K1G 1R7",860 Colson,Ottawa,Ontario,K1G 1R7
Y,CEPEO,Elementary,École élémentaire publique Maurice-Lapointe,"17 BRIDGESTONE, KANATA, Ontario, K2M 0E9",17 BRIDGESTONE,KANATA,Ontario,K2M 0E9
Y,CEPEO,Elementary,École élémentaire publique Michaëlle-Jean,"11 Claridge, Barrhaven, Ontario, K2J 5A3",11 Claridge,Barrhaven,Ontario,K2J 5A3
Y,CEPEO,Elementary,École élémentaire publique Mille-Iles,"72 Gilmour, Kingston, Ontario, K7M 9G6",72 Gilmour,Kingston,Ontario,K7M 9G6
Y,CEPEO,Elementary,École élémentaire publique Nouvel Horizon,"433 Cartier, Hawkesbury, Ontario, K6A 1V9",433 Cartier,Hawkesbury,Ontario,K6A 1V9
Y,CEPEO,Elementary,École élémentaire publique Omer-Deslauriers,"159 Chesterton, Nepean, Ontario, K2E 7E6",159 Chesterton,Nepean,Ontario,K2E 7E6
Y,CEPEO,Elementary,École élémentaire publique Riverside Sud,"715 Brian Good, Ottawa, Ontario, K4M 1B2",715 Brian Good,Ottawa,Ontario,K4M 1B2
Y,CEPEO,Elementary,École élémentaire publique Rose des Vents,"1650 2ième Rue Est, Cornwall, Ontario, K6H 2C3",1650 2ième Rue Est,Cornwall,Ontario,K6H 2C3
Y,CEPEO,Elementary,École élémentaire publique Séraphin-Marion,"2147 Loyola, Ottawa, Ontario, K1J 7W3",2147 Loyola,Ottawa,Ontario,K1J 7W3
Y,CEPEO,Elementary,École élémentaire publique Terre des Jeunes,"33 Lochiel Est, Alexandria, Ontario, K0C 1A0",33 Lochiel Est,Alexandria,Ontario,K0C 1A0
Y,CEPEO,Elementary,École élémentaire publique Trille des Bois,"140 Genest, Vanier, Ontario, K1L 7Y9",140 Genest,Vanier,Ontario,K1L 7Y9
Y,Private,K-12,Ottawa Islamic School,"10 Coral Ave, Nepean, ON K2E 5Z6",10 Coral Ave,Nepean,Ontario,K2E 5Z6
Y,,Post-Secondary,University of Ottawa,"75 Laurier Ave E, Ottawa, ON K1N 6N5",75 Laurier Ave E,Ottawa,Ontario,K1N 6N5
N,OCDSB,Elementary,McGregor Easson Public School,"991 Dynes Rd, Ottawa, ON K2C 0H2",991 Dynes Rd,Ottawa,Ontario,K2C 0H2
Y,,Post-Secondary,Algonquin College,"1385 Woodroffe Avenue, Ottawa, ON K2G 1V8",1385 Woodroffe Avenue,Ottawa,Ontario,K2G 1V8
Y,Private,K-12,Ashbury College,"362 Mariposa Avenue, Ottawa, ON K1M 0T3",362 Mariposa Avenue,Ottawa,Ontario,K1M 0T3
Y,Private,Elementary,Abraar Elementary School,"70 Fieldrow Street, Ottawa, ON K2G 2Y7",70 Fieldrow Street,Ottawa,Ontario,K2G 2Y7
Y,Private,Secondary,Abraar Secondary School,"1085 Grenon Avenue, Ottawa, ON K2B 8L7",1085 Grenon Avenue,Ottawa,Ontario,K2B 8L7
N,OCSB,Intermediate,Jean Vanier Catholic Intermediate,"320 Lajoie St, Vanier, ON K1L 6L8",320 Lajoie St,Ottawa,Ontario,K1L 6L8
N,OCSB,Elementary,Christie PS,"798 Lyon St, Ottawa, ON K1S 5H5",798 Lyon St,Ottawa,Ontario,K1S 5H5
Y,,Post-Secondary,Carleton University,"1125 Colonel By Dr, Ottawa, ON K1S 5B6",1125 Colonel By Dr,Ottawa,Ontario,K1S 5B6
N,OCDSB,Secondary,Laurentian High School,"1357 Baseline Rd, Ottawa, ON  K2C 0A8",1357 Baseline Rd,Ottawa,Ontario,K2C 0A8
N,OCDSB,Elementary,Parkwood Hills PS,"60 Tiverton Drive, Nepean, ON K2E 6L8",60 Tiverton Drive,Ottawa,Ontario,K2E 6L8
Y,,Post-Secondary,Queens University,"99 University Ave, Kingston, ON K7L 3N6", 99 University Ave,Kingston,Ontario,K7L 3N6
Y,,Post-Secondary,La Cité collégiale,"801 Aviation Pkwy, Ottawa, ON K1K 4R3",801 Aviation Pkwy,Ottawa,Ontario,K1K 4R3
N,OCDSB,Secondary,J.S. Woodsworth HS,"159 Chesterton Drive, Ottawa, ON K2E 7E6 ",159 Chesterton Drive,Ottawa,Ontario,K2E 7E6 
N,,Post-Secondary,"Everest College of Business, Technology and Health Care","19 - 1200 St Laurent Blvd, Ottawa, ON K1K 3B8",19 - 1200 St Laurent Blvd,Ottawa,Ontario,K1K 3B8
N,OCDSB,Elementary,Munster ES,"7816 Bleeks Rd, Goulbourn, ON",7816 Bleeks Rd,Goulbourn,Ontario,K0A 3P0
N,OCDSB,Elementary,Queenswood PS,"1445 Duford Dr; Orleans, Ontario K1E 1E8",1445 Duford Dr,Ottawa,Ontario,K1E 1E8
Y,,Private,Debbie Campbell Academy,"440 Slater St, Ottawa, ON K1R 5B5",440 Slater St, Ottawa,Ontario,K1R 5B5
`;
  
  /**
   * Make school nodes.
   * @see https://stackoverflow.com/questions/8493195/how-can-i-parse-a-csv-string-with-javascript-which-contains-comma-in-data, RFC 4180 solution
   * @param schools 
   * @param deleteSchoolsCsv whether or not to delete $wnd.xh.schools.csv
  */
  $wnd.xh.schools.makeSchoolNodes = function(schools, deleteSchoolsCsv) {
    'use strict';
    
    function csvToArray(text) {
      let p = '', row = [''], ret = [row], i = 0, r = 0, s = !0, l;
      for (l of text) {
        if ('"' === l) {
          if (s && l === p) row[i] += l;
          s = !s;
        } else if (',' === l && s) l = row[++i] = '';
        else if ('\n' === l && s) {
          if ('\r' === p) row[i] = row[i].slice(0, -1);
          row = ret[++r] = [l = '']; i = 0;
        } else row[i] += l;
        p = l;
      }
      return ret;
    };
    
    let text = $wnd.xh.schools.csv;
    //schools.println(text);
    
    let csvarrs = csvToArray(text);
    
    var xmlstr = "<_-.schools>\n";
    // zeroth row is a header row
    for (var i = 1; i < csvarrs.length; i++) {
      var csvarr = csvarrs[i];
      var stype = csvarr[2];
      var sname = csvarr[3];
      var spcode = csvarr[8];
      xmlstr += '<' + stype + ' roleName="' + sname + '" pcode="' + spcode + '" keep="false"/>\n';
    }
    xmlstr += "</_-.schools>\n";
    schools.append(xmlstr);
    
    if (deleteSchoolsCsv) {
      $wnd.xh.schools.csv = null;
    }
  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  // Postal Codes
  $wnd.xh.pcodes = {};
  
  // Postal Codes - XML string
  // these postal codes do not exist: K1D K1F K1I K1O K1Q K1U K2D K2F K2I K2O K2Q K2U K2X
  $wnd.xh.pcodes.xmlstr = `
<_-.pcodes>
  <PostalCode roleName="K0A"><Geo pcode="K0A">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.663596,45.428647]}}</Geo></PostalCode>
  <PostalCode roleName="K0C"><Geo pcode="K0C">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-74.485409,45.132577]}}</Geo></PostalCode>
  <PostalCode roleName="K0G"><Geo pcode="K0G">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.643371,45.014736]}}</Geo></PostalCode>
  <PostalCode roleName="K1A"><Geo pcode="K1A">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.728508,45.348197]}}</Geo></PostalCode>
  <PostalCode roleName="K1B"><Geo pcode="K1B">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.622306,45.416433]}}</Geo></PostalCode>
  <PostalCode roleName="K1C"><Geo pcode="K1C">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.55094,45.480599]}}</Geo></PostalCode>
  <PostalCode roleName="K1E"><Geo pcode="K1E">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.511317,45.480709]}}</Geo></PostalCode>
  <PostalCode roleName="K1G"><Geo pcode="K1G">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.639105,45.415407]}}</Geo></PostalCode>
  <PostalCode roleName="K1H"><Geo pcode="K1H">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.673984,45.385008]}}</Geo></PostalCode>
  <PostalCode roleName="K1J"><Geo pcode="K1J">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.626478,45.423772]}}</Geo></PostalCode>
  <PostalCode roleName="K1K"><Geo pcode="K1K">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.630271,45.457049]}}</Geo></PostalCode>
  <PostalCode roleName="K1L"><Geo pcode="K1L">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.654936,45.429783]}}</Geo></PostalCode>
  <PostalCode roleName="K1M"><Geo pcode="K1M">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.68582,45.45387]}}</Geo></PostalCode>
  <PostalCode roleName="K1N"><Geo pcode="K1N">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.693092,45.42963]}}</Geo></PostalCode>
  <PostalCode roleName="K1P"><Geo pcode="K1P">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.698843,45.421395]}}</Geo></PostalCode>
  <PostalCode roleName="K1R"><Geo pcode="K1R">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.709934,45.413241]}}</Geo></PostalCode>
  <PostalCode roleName="K1S"><Geo pcode="K1S">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.678998,45.418205]}}</Geo></PostalCode>
  <PostalCode roleName="K1T"><Geo pcode="K1T">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.606831,45.317154]}}</Geo></PostalCode>
  <PostalCode roleName="K1V"><Geo pcode="K1V">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.660618,45.349132]}}</Geo></PostalCode>
  <PostalCode roleName="K1W"><Geo pcode="K1W">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.522305,45.426208]}}</Geo></PostalCode>
  <PostalCode roleName="K1X"><Geo pcode="K1X">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.630618,45.294871]}}</Geo></PostalCode>
  <PostalCode roleName="K1Y"><Geo pcode="K1Y">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.749954,45.4022]}}</Geo></PostalCode>
  <PostalCode roleName="K1Z"><Geo pcode="K1Z">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.74585,45.379869]}}</Geo></PostalCode>
  <PostalCode roleName="K2A"><Geo pcode="K2A">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.824451,45.192827]}}</Geo></PostalCode>
  <PostalCode roleName="K2B"><Geo pcode="K2B">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.790284,45.352333]}}</Geo></PostalCode>
  <PostalCode roleName="K2C"><Geo pcode="K2C">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.710576,45.3810479]}}</Geo></PostalCode>
  <PostalCode roleName="K2E"><Geo pcode="K2E">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.700554,45.334394]}}</Geo></PostalCode>
  <PostalCode roleName="K2G"><Geo pcode="K2G">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.73508,45.33583]}}</Geo></PostalCode>
  <PostalCode roleName="K2H"><Geo pcode="K2H">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.795962,45.337119]}}</Geo></PostalCode>
  <PostalCode roleName="K2J"><Geo pcode="K2J">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.741369,45.273596]}}</Geo></PostalCode>
  <PostalCode roleName="K2K"><Geo pcode="K2K">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.905028,45.334941]}}</Geo></PostalCode>
  <PostalCode roleName="K2L"><Geo pcode="K2L">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.899853,45.31074]}}</Geo></PostalCode>
  <PostalCode roleName="K2M"><Geo pcode="K2M">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.846309,45.28232]}}</Geo></PostalCode>
  <PostalCode roleName="K2N"><Geo pcode="K2N">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.492422,45.458121]}}</Geo></PostalCode>
  <PostalCode roleName="K2P"><Geo pcode="K2P">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.69513,45.419597]}}</Geo></PostalCode>
  <PostalCode roleName="K2R"><Geo pcode="K2R">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.808699,45.274425]}}</Geo></PostalCode>
  <PostalCode roleName="K2S"><Geo pcode="K2S">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.921991,45.246838]}}</Geo></PostalCode>
  <PostalCode roleName="K2T"><Geo pcode="K2T">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.920189,45.307536]}}</Geo></PostalCode>
  <PostalCode roleName="K2V"><Geo pcode="K2V">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.913951,45.302428]}}</Geo></PostalCode>
  <PostalCode roleName="K2W"><Geo pcode="K2W">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-76.002565,45.361352]}}</Geo></PostalCode>
  <PostalCode roleName="K2Y"><Geo pcode="K2Y">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.912345,45.302734]}}</Geo></PostalCode>
  <PostalCode roleName="K2Z"><Geo pcode="K2Z">{"type":"Feature","properties":{"width":20,"height":20},"geometry":{"type":"Point","coordinates":[-75.7333806945,45.3616334385]}}</Geo></PostalCode>
</_-.pcodes>
`;

  /**
   * Make postal code nodes.
   * @param pcodes 
   * @param deletePcodesXml whether or not to delete $wnd.xh.pcodes.xmlstr
  */
  $wnd.xh.pcodes.makePostalCodeNodes = function(pcodes, deletePcodesXml) {
    let xmlstr = $wnd.xh.pcodes.xmlstr;
    //pcodes.println(xmlstr);
    pcodes.append(xmlstr);
    
    if (deletePcodesXml) {
      $wnd.xh.pcodes.xmlstr = null;
    }
  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  // Clubhouses
  $wnd.xh.clubhouses = {};
  
  // Clubhouses - XML string
  $wnd.xh.clubhouses.xmlstr = `
<_-.chouses>
  <Clubhouse roleName="BRIT" pcode="K2B 7W3"><Anno>BRIT Ron Kolbus Clubhouse</Anno>
    <Program roleName="Homework Club" programid="13"/>
    <Program roleName="Dodge Ball" programid="1"/>
    <Program roleName="Open Gym" programid="2"/>
    <Program roleName="Volleyball" programid="3"/>
    <Program roleName="Computer Time" programid="4"/>
    <Program roleName="Movie" programid="5"/>
    <Program roleName="Friday" programid="6"/>
    <Program roleName="HWC001" programid="7"/>
    <Program roleName="generic"/>
  </Clubhouse>
  <Clubhouse roleName="MC" pcode="K1K 1G6"><Anno>MC Don McGahan Clubhouse</Anno>
    <Program roleName="13to18"/>
    <Program roleName="25to49"/>
    <Program roleName="Adult"/>
    <Program roleName="AirHockey"/>
    <Program roleName="AQUA001"/>
    <Program roleName="ART001"/>
    <Program roleName="Artattack"/>
    <Program roleName="Art Child"/>
    <Program roleName="artmag"/>
    <Program roleName="ArtMagic"/>
    <Program roleName="ArtNow"/>
    <Program roleName="Artnow"/>
    <Program roleName="artscrafts"/>
    <Program roleName="Awesome"/>
    <Program roleName="AWP"/>
    <Program roleName="Badminton"/>
    <Program roleName="Ballhockey"/>
    <Program roleName="Basketeers"/>
    <Program roleName="B-Ball"/>
    <Program roleName="BBall"/>
    <Program roleName="B-Ball Int"/>
    <Program roleName="B-Ball Jr"/>
    <Program roleName="BballLeag"/>
    <Program roleName="bgcleader"/>
    <Program roleName="bgcswimc"/>
    <Program roleName="bgcswiml"/>
    <Program roleName="bgcswimo"/>
    <Program roleName="BGC WEEK"/>
    <Program roleName="Board"/>
    <Program roleName="Boardcard"/>
    <Program roleName="BoardGames"/>
    <Program roleName="Bolts"/>
    <Program roleName="BookClub"/>
    <Program roleName="Boys Group"/>
    <Program roleName="BoysLeag"/>
    <Program roleName="Bracelets"/>
    <Program roleName="Camp1"/>
    <Program roleName="Camp2"/>
    <Program roleName="Camp3"/>
    <Program roleName="Camp4"/>
    <Program roleName="Canteen"/>
    <Program roleName="challnge"/>
    <Program roleName="CHOIR1"/>
    <Program roleName="Club"/>
    <Program roleName="Clubhouse"/>
    <Program roleName="clubhouse"/>
    <Program roleName="Co-EdBBALL"/>
    <Program roleName="CompClub"/>
    <Program roleName="Computers"/>
    <Program roleName="Cooking"/>
    <Program roleName="Cool Moves"/>
    <Program roleName="CREATE"/>
    <Program roleName="cycle"/>
    <Program roleName="DanceParty"/>
    <Program roleName="Diver"/>
    <Program roleName="Dodge"/>
    <Program roleName="drama"/>
    <Program roleName="Drop in JR"/>
    <Program roleName="Employer"/>
    <Program roleName="ESL"/>
    <Program roleName="Exerclas"/>
    <Program roleName="FabFit"/>
    <Program roleName="fish"/>
    <Program roleName="FLAG001"/>
    <Program roleName="Flagfoot"/>
    <Program roleName="Foosball"/>
    <Program roleName="FormGiven"/>
    <Program roleName="Fund"/>
    <Program roleName="Funfriday"/>
    <Program roleName="GameDesign"/>
    <Program roleName="Gamers"/>
    <Program roleName="Gaming"/>
    <Program roleName="Gardening"/>
    <Program roleName="girlsjwhf"/>
    <Program roleName="GIRLSNIGHT"/>
    <Program roleName="Girlz Only"/>
    <Program roleName="GLO"/>
    <Program roleName="Heros"/>
    <Program roleName="Hockdrop"/>
    <Program roleName="Homework"/>
    <Program roleName="Hoops"/>
    <Program roleName="HOOPS001"/>
    <Program roleName="HWC001"/>
    <Program roleName="HWC002"/>
    <Program roleName="Improv"/>
    <Program roleName="INT BBALL"/>
    <Program roleName="INTEG"/>
    <Program roleName="Inthoops"/>
    <Program roleName="JJ001"/>
    <Program roleName="JJ002"/>
    <Program roleName="Junior Rec"/>
    <Program roleName="Just Jrs"/>
    <Program roleName="JustJrs"/>
    <Program roleName="karaoke"/>
    <Program roleName="L4L"/>
    <Program roleName="L4LIFE"/>
    <Program roleName="larocque"/>
    <Program roleName="LCA"/>
    <Program roleName="Leaders"/>
    <Program roleName="League"/>
    <Program roleName="Lego"/>
    <Program roleName="MBNA"/>
    <Program roleName="membersgym"/>
    <Program roleName="Money"/>
    <Program roleName="Movie"/>
    <Program roleName="Music"/>
    <Program roleName="OffSupp"/>
    <Program roleName="Open gym"/>
    <Program roleName="Origami"/>
    <Program roleName="Outreach"/>
    <Program roleName="OYC"/>
    <Program roleName="Painting"/>
    <Program roleName="PAL"/>
    <Program roleName="PALBB"/>
    <Program roleName="PALBBall"/>
    <Program roleName="PALBH"/>
    <Program roleName="PAL HOCKEY"/>
    <Program roleName="palisc"/>
    <Program roleName="PALSC"/>
    <Program roleName="PAL Soccer"/>
    <Program roleName="PALVB"/>
    <Program roleName="PerfArt"/>
    <Program roleName="Photo"/>
    <Program roleName="PingPong"/>
    <Program roleName="PlantSeeds"/>
    <Program roleName="Pool"/>
    <Program roleName="QEBUS"/>
    <Program roleName="Racquet"/>
    <Program roleName="RecRelax"/>
    <Program roleName="Reel"/>
    <Program roleName="ReelLife"/>
    <Program roleName="ReelLifeAd"/>
    <Program roleName="rhymdance"/>
    <Program roleName="RockStar"/>
    <Program roleName="Rogers"/>
    <Program roleName="RRTG"/>
    <Program roleName="runclub"/>
    <Program roleName="SatNgtHoky"/>
    <Program roleName="Scholar"/>
    <Program roleName="sewcoo"/>
    <Program roleName="Shine"/>
    <Program roleName="Ski"/>
    <Program roleName="SKILLSCOMP"/>
    <Program roleName="Snack"/>
    <Program roleName="SOAR"/>
    <Program roleName="Soccer"/>
    <Program roleName="SoccerLea"/>
    <Program roleName="spec ev"/>
    <Program roleName="Special"/>
    <Program roleName="SrBBall"/>
    <Program roleName="SRGirls"/>
    <Program roleName="SSLLBBALL"/>
    <Program roleName="SSLLBHOCK"/>
    <Program roleName="SSLLSOC"/>
    <Program roleName="Staffgym"/>
    <Program roleName="STARS"/>
    <Program roleName="S.T.E.P."/>
    <Program roleName="S.TE.P."/>
    <Program roleName="Supper"/>
    <Program roleName="SuppPart"/>
    <Program roleName="SWIM"/>
    <Program roleName="SwimLesson"/>
    <Program roleName="Swimming"/>
    <Program roleName="SWIM TEAM"/>
    <Program roleName="tennis"/>
    <Program roleName="TL"/>
    <Program roleName="TorchClub"/>
    <Program roleName="Turf"/>
    <Program roleName="turf"/>
    <Program roleName="Tutor"/>
    <Program roleName="Volleyball"/>
    <Program roleName="VolServ"/>
    <Program roleName="Volunteer"/>
    <Program roleName="walkbus"/>
    <Program roleName="Water"/>
    <Program roleName="Wii"/>
    <Program roleName="wthurs"/>
    <Program roleName="WTW"/>
    <Program roleName="YC Int"/>
    <Program roleName="YC Jr"/>
    <Program roleName="YC Ott"/>
    <Program roleName="YCOUNCIL"/>
    <Program roleName="YC Sr"/>
    <Program roleName="Yoga"/>
    <Program roleName="Youth"/>
    <Program roleName="generic"/>
  </Clubhouse>
  <Clubhouse roleName="PYC" pcode="K2C 1N7"><Anno>PYC Tomlinson Family Foundation Clubhouse</Anno> <!-- formerly Police Youth Centre -->
    <Program roleName="artscrafts"/>
    <Program roleName="AWP"/>
    <Program roleName="Badminton"/>
    <Program roleName="Ballhockey"/>
    <Program roleName="B-Ball Jr"/>
    <Program roleName="bgcswiml"/>
    <Program roleName="BookClub"/>
    <Program roleName="Book club"/>
    <Program roleName="Club"/>
    <Program roleName="clubhouse"/>
    <Program roleName="CompClub"/>
    <Program roleName="Cooking"/>
    <Program roleName="Cool Moves"/>
    <Program roleName="Dodge"/>
    <Program roleName="drama"/>
    <Program roleName="Flagfoot"/>
    <Program roleName="FormGiven"/>
    <Program roleName="Friday"/>
    <Program roleName="FunFriday"/>
    <Program roleName="Funfriday"/>
    <Program roleName="funsun"/>
    <Program roleName="Gard"/>
    <Program roleName="GardenClub"/>
    <Program roleName="Gardening"/>
    <Program roleName="girlsjwhf"/>
    <Program roleName="Girlz Only"/>
    <Program roleName="GLO"/>
    <Program roleName="GOAL"/>
    <Program roleName="Homework"/>
    <Program roleName="HWC001"/>
    <Program roleName="Inthoops"/>
    <Program roleName="keyclub"/>
    <Program roleName="League"/>
    <Program roleName="MBNA"/>
    <Program roleName="membersgym"/>
    <Program roleName="Money"/>
    <Program roleName="Movie"/>
    <Program roleName="Music"/>
    <Program roleName="N and L"/>
    <Program roleName="Omnikin"/>
    <Program roleName="outdrclub"/>
    <Program roleName="PALBB"/>
    <Program roleName="PALSC"/>
    <Program roleName="Photo"/>
    <Program roleName="RRTG"/>
    <Program roleName="Soccer"/>
    <Program roleName="sportkid"/>
    <Program roleName="TorchClub"/>
    <Program roleName="Volleyball"/>
    <Program roleName="WTW"/>
    <Program roleName="Yoga"/>
    <Program roleName="generic"/>
  </Clubhouse>
  <Clubhouse roleName="GLO" pcode="K1J 7N8"><Anno>GLO Gloucester High School</Anno>
    <Program roleName="Homework Club" programid="13"/>
    <Program roleName="Movie" programid="1"/>
    <Program roleName="Friday" programid="2"/>
    <Program roleName="HWC001" programid="3"/>
    <Program roleName="generic"/>
  </Clubhouse>
  <Clubhouse roleName="HEA" pcode="K1V 8Z3"><Anno>HEA Heatherington</Anno>
    <Program roleName="Homework Club" programid="13"/>
    <Program roleName="Movie" programid="1"/>
    <Program roleName="Friday" programid="2"/>
    <Program roleName="HWC001" programid="3"/>
    <Program roleName="generic"/>
  </Clubhouse>
  <Clubhouse roleName="RGM" pcode="K1V 7T3"><Anno>RGM Ridgemont High School</Anno>
    <Program roleName="Homework Club" programid="13"/>
    <Program roleName="Movie" programid="1"/>
    <Program roleName="Friday" programid="2"/>
    <Program roleName="HWC001" programid="3"/>
    <Program roleName="generic"/>
  </Clubhouse>
  <Clubhouse roleName="ROC" pcode="K1R 7B2"><Anno>ROC Rochester Heights</Anno>
    <Program roleName="Homework Club" programid="13"/>
    <Program roleName="Movie" programid="1"/>
    <Program roleName="Friday" programid="2"/>
    <Program roleName="HWC001" programid="3"/>
    <Program roleName="generic"/>
  </Clubhouse>
  <Clubhouse roleName="RID" pcode="K1Z 6B9"><Anno>RID Rideau High School</Anno> <!-- no longer active ?  I am using a dummy pcode -->
    <Program roleName="Homework Club" programid="13"/>
    <Program roleName="Movie" programid="1"/>
    <Program roleName="Friday" programid="2"/>
    <Program roleName="HWC001" programid="3"/>
    <Program roleName="generic"/>
  </Clubhouse>
  <Clubhouse roleName="BRC" pcode="K1Z 6B9"><Anno>BRC BRC</Anno> <!-- no longer active ?  I am using a dummy pcode-->
    <Program roleName="Homework Club" programid="13"/>
    <Program roleName="Movie" programid="1"/>
    <Program roleName="Friday" programid="2"/>
    <Program roleName="HWC001" programid="3"/>
    <Program roleName="generic"/>
  </Clubhouse>
  <!--<Clubhouse roleName="Camp Smitty"/>-->
</_-.chouses>
`;
  
  /**
   * Make clubhouse nodes.
   * @param clubhouses 
   * @param deleteChousesXml whether or not to delete $wnd.xh.clubhouses.xmlstr
  */
  $wnd.xh.clubhouses.makeClubhouseNodes = function(clubhouses, deleteChousesXml) {
    let xmlstr = $wnd.xh.clubhouses.xmlstr;
    //clubhouses.println(xmlstr);
    clubhouses.append(xmlstr);
    
    if (deleteChousesXml) {
      $wnd.xh.clubhouses.xmlstr = null;
    }
  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  // PostalCodesbehavior
  $wnd.xh.PostalCodesbehavior = function PostalCodesbehavior() {}
  
  $wnd.xh.PostalCodesbehavior.prototype.postConfigure = function() {
    const LOCATE_CH_IN_PC  = true;  // whether or not to locate each Clubhouse within its PostalCode node
    const LOCATE_SCH_IN_PC = true; // whether or not to locate each School within its PostalCode node
    var me, houses, schools, clubhouses;
    //postConfigure: function() {
    me = this.cnode.parent();
    this.cnode.remove();
    houses = me.xpath("../Houses");
    schools = me.xpath("../Schools");
    clubhouses = me.xpath("../Clubhouses");
    
    var chouse = clubhouses.first();
    while (chouse) {
      var nextChouse = chouse.next();
      if (chouse.pcode && chouse.pcode.length >= 3) {
        var roleName = chouse.pcode.substring(0, 3);
        var xmlstr = '<PostalCode roleName="' + roleName + '"></PostalCode>';
        me.obj(this.cnode.append(xmlstr).last().remove());
        // optionally append chouse to the postal code node
        if (LOCATE_CH_IN_PC) {
          var pcnode = me.xpath("*[@roleName='" + roleName + "']");
          if (pcnode) {
            clubhouses.cache.push(chouse);
            pcnode.append(chouse.remove());
          }
        }
      }
      chouse = nextChouse;
    }
    
    var school = schools.first();
    while (school) {
      var nextSchool = school.next();
      if (school.pcode && school.pcode.length >= 3) {
        var roleName = school.pcode.substring(0, 3);
        var xmlstr = '<PostalCode roleName="' + roleName + '"></PostalCode>';
        me.obj(this.cnode.append(xmlstr).last().remove());
        // optionally append school to the postal code node
        if (LOCATE_SCH_IN_PC) {
          var pcnode = me.xpath("*[@roleName='" + roleName + "']");
          if (pcnode) {
            schools.cache.push(school);
            pcnode.append(school.remove());
          }
        }
      }
      school = nextSchool;
    }
  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  // Membersbehavior
  const MYHOUSE = "myhouse";
  const MYSCHOOL = "myschool";
  const MYCLUBHOUSE = "myclubhouse";
  const MYDECISION = "decision"; // whether or not to go to a clubhouse
  const UNKEEP_SCHOOLS = true;
  
  $wnd.xh.Membersbehavior = function Membersbehavior() {}
  
  $wnd.xh.Membersbehavior.prototype.postConfigure = function() {
    this.me = this.cnode.parent();
    this.members = this.me;
    
    this.countChildren = function(node) {
      if (!node) {return 0;}
      var count = 0;
      var child = node.first();
      while (child) {
        count++;
        child = child.next();
      }
      return count;
    }; // end this.countChildren = function(node)

    // remove schools that no members attend
    this.unkeepSchools = function(schools) {
      var school = schools.first();
      while (school) {
        var nextSchool = school.next();
        if (school.keep == "false") {
          school.remove();
        }
        school = nextSchool;
      }
    }; // end this.unkeepSchools = function(schools)

    // initialize all child nodes, as Member nodes
    // TODO schools and clubhouses are in their permanent places by the time act() is called; this effects new members who are dropped in
    // Note re myclubhouseIx:
    //   XPath is 1-based   ex: clubhouses.xpath("Clubhouse[3]")
    //   while a JavaScript array is zero-based  ex: clubhouses.cache[2]
    this.initMembers = function() {
      var member = this.members.first();
      while (member) {
        if (member == this.cnode) {
          member = member.next();
        }
        else if (member.xhc().name() == "Attribute_String") {
          // this is probably raw data queried from postgreSQL  d4g_member_id,timestep,myclubhouse,aprogram
          this.parseAttributeString(member);
          var nextmember = member.next();
          member.remove();
          member = nextmember;
        }
        else {
          member[MYHOUSE] = this.houses.xpath("House[" + (member[MYHOUSE] || Math.ceil(Math.random() * this.hcount)) + "]");
          if (!member[MYHOUSE]) {
            member[MYHOUSE] = this.houses.xpath("House[" + (Math.ceil(Math.random() * this.hcount)) + "]");
          }
          member[MYSCHOOL] = this.schools.xpath("*[" + (member[MYSCHOOL] || Math.ceil(Math.random() * this.scount)) + "]");
          if (member[MYSCHOOL]) {
            member[MYSCHOOL].keep = "true";
          }
          else {
            member[MYSCHOOL] = this.schools.cache[Math.floor(Math.random() * schools.cache.length)];
          }
          
          var myclubhouseIx = member[MYCLUBHOUSE];
          //me.println("Looking for clubhouse " + myclubhouseIx);
          if (Number(myclubhouseIx) != myclubhouseIx) {
            member[MYCLUBHOUSE] = this.clubhouses.xpath("Clubhouse[@roleName='" + myclubhouseIx + "']");
            if (!member[MYCLUBHOUSE]) {
              switch (myclubhouseIx) {
                case "BRIT": myclubhouseIx = 0; break;
                case "MC":   myclubhouseIx = 1; break;
                case "PYC":  myclubhouseIx = 2; break;
                case "GLO":  myclubhouseIx = 3; break;
                case "HEA":  myclubhouseIx = 4; break;
                case "RGM":  myclubhouseIx = 5; break;
                case "ROC":  myclubhouseIx = 6; break;
                case "RID":  myclubhouseIx = 7; break;
                case "BRC":  myclubhouseIx = 8; break;
                default:
                  me.println("Cannot find clubhouse " + myclubhouseIx + ". Setting it to default MC 1."); // ex: "BL"
                  myclubhouseIx = 1; // "MC" has been the most attended clubhouse
                  break;
              }
              member[MYCLUBHOUSE] = this.clubhouses.cache[(myclubhouseIx) || (Math.floor(Math.random() * this.clubhouses.cache.length))];
            }
          }
          else {
            member[MYCLUBHOUSE] = this.clubhouses.xpath("Clubhouse[" + (myclubhouseIx || Math.ceil(Math.random() * this.ccount)) + "]");
            if (!member[MYCLUBHOUSE]) {
              member[MYCLUBHOUSE] = this.clubhouses.cache[(myclubhouseIx - 1) || (Math.floor(Math.random() * this.clubhouses.cache.length))];
            }
          }
          
          member[MYDECISION] = true;
          member.action("param repeat false -1,10;param transcript false;param debug false;param actTimeStep 0;param subtrees true ToolsST,BehaviorsST;become this isactive false;");
          this.me.println(member.role() + "," + member[MYHOUSE].id() + "," + member[MYSCHOOL].role() + "," + member[MYCLUBHOUSE].role() + "," + member["isactive"]);
          var nextmember = member.next();
          member[MYHOUSE].append(member.remove());
          // set the val of each member, to the value of its id, as required by the line-chart plotter
          member.val(member.id());
          member = nextmember;
        }
      }
    }; // this.initMembers = function()

    /**
     * Parse an Attribute_String node, which may contain CSV data.
     * example:
    d4g_member_id,timestep,myclubhouse,aprogram
    -123,2016187,MC,Club
    -123,2016188,MC,Club
    -123,2016188,MC,artscrafts
    */
    this.parseAttributeString = function(node) {
      this.me.println(node.name());
      var str = node.text();
      if (str) {
        var recordArr = str.split("\n");
        this.me.println(recordArr.length + " records");
        this.me.println("0 " + recordArr[0]);
        this.me.println("1 " + recordArr[1]);
        this.me.println("n " + recordArr[recordArr.length - 1]);
        var memberid = 0;
        var membernameSuffix = 101;
        var myhouse = 1;
        var myschool = 1;
        var xmlstr = null;
        for (var i = 1; i < recordArr.length; i++) {
          var record = recordArr[i];
          var fields = record.split(",");
          if (fields[0] != memberid) {
            if (xmlstr) {
              xmlstr += '    ]]' + '></Attribute_String>\n';
              xmlstr += '  </DbAttendRecs>\n';
              xmlstr += '</Member>\n';
              //me.println(xmlstr);
              members.append(xmlstr);
            }
            memberid = fields[0];
            xmlstr = '<Member roleName="A' + membernameSuffix + '" memberid="' + memberid + '" myhouse="' + myhouse + '" myschool="' + myschool + '" myclubhouse="' + fields[2] + '"><Color>green</Color>\n';
            xmlstr += '  <DbAttendRecs>\n';
            xmlstr += '    <DbAttendRecParser></DbAttendRecParser>\n';
            xmlstr += '    <Attribute_String>' + '<' + '![CDATA[\n';
            xmlstr += 'timestep,myclubhouse,aprogram\n';
            myhouse++;
            myschool++;
            membernameSuffix++;
          }
          xmlstr += fields[1] + ',' + fields[2] + ',' + fields[3] + '\n';
        }
      } // end if (str)
    }; // end this.parseAttributeString = function(node)
    
    //this.cnode.remove();
    $wnd.xh.param("TimeStep", this.members.startTimeStep); // "20052700"); // 20052700  year 2005, day-of-year 270, time-of-day (from 0 to 9) 0  should match Avatar "param actTimeStep 0;" or " 20052700;"
    this.houses = this.me.xpath("../City/Houses");
    this.schools = this.me.xpath("../City/Schools");
    this.schools.cache = [];
    this.clubhouses = this.me.xpath("../City/Clubhouses");
    this.clubhouses.cache = [];
    this.hcount = this.countChildren(this.houses);
    this.scount = this.countChildren(this.schools);
    this.ccount = this.countChildren(this.clubhouses);
    this.me.println("" + this.hcount + " " + this.houses);
    this.me.println("" + this.scount + " " + this.schools);
    this.me.println("" + this.ccount + " " + this.clubhouses);
    this.me.println("myname,myhouse,myschool,myclubhouse");
    this.initMembers(); // initialize Members whose nodes are already there
    if (UNKEEP_SCHOOLS) {
      this.unkeepSchools(this.schools);
    }
  }; // end $wnd.xh.Membersbehavior.prototype.postConfigure = function()

  $wnd.xh.Membersbehavior.prototype.act = function() {
    //this.me.println($wnd.xh.param("TimeStep"));
    this.initMembers(); // initialize Members whose nodes have just been drag-and-dropped or pasted in
  }; // end $wnd.xh.Membersbehavior.prototype.act = function()

})(); // end (function()
