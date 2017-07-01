<?php
//header('Content-type: application/x-www-form-urlencoded'); // text/plain or html ?
header('Content-type: text/plain');
// $encodedAqlCode is from the browser
// http://208.113.133.193/cgi-bin/try.cgi?code=typeside%20Ty%20=%20literal%20%7B%0A%20%20java_types

// this works
//$encodedAqlCode = "code=typeside+Ty+%3D+literal+%7B+%0D%0A%09java_types%0D%0A%09%09String+%3D+%22java.lang.String%22%0D%0A%09java_constants%0D%0A%09%09String+%3D+%22return+input%5B0%5D%22%0D%0A%7D%0D%0A%0D%0Aschema+NormalizedSchema+%3D+literal+%3A+Ty+%7B%0D%0A%09entities%0D%0A%09%09Male+%0D%0A%09%09Female%0D%0A%09foreign_keys%0D%0A%09%09mother+%3A+Male+-%3E+Female%0D%0A++%09attributes%0D%0A++%09%09female_name+%3A+Female+-%3E+String%0D%0A++%09%09male_name+%3A+Male+-%3E+String+%0D%0A%7D%0D%0A%0D%0Ainstance+NormalizedData+%3D+literal+%3A+NormalizedSchema+%7B%0D%0A%09generators%0D%0A%09%09Al+Bob+Charlie+%3A+Male%0D%0A%09%09Ellie+Fran+%3A+Female%0D%0A%09equations%0D%0A%09%09Al.male_name+%3D+Albert+%0D%0A%09%09Al.mother+%3D+Ellie%0D%0A%09%09%0D%0A%09%09Bob.male_name+%3D+George%0D%0A%09%09Bob.mother+%3D+Ellie%0D%0A%09%09%0D%0A%09%09Charlie.male_name+%3D+Charles%09%0D%0A%09%09Charlie.mother+%3D+Fran%0D%0A%0D%0A%09%09Ellie.female_name+%3D+Elaine%0D%0A%09%09Fran.female_name+%3D+Francine%0D%0A%7D%0D%0A%0D%0Aschema+DeNormalizedSchema+%3D+literal+%3A+Ty+%7B%0D%0A%09imports%0D%0A%09%09NormalizedSchema%0D%0A%09attributes%0D%0A%09%09mother_name+%3A+Male+-%3E+String%0D%0A++%09observation_equations%0D%0A++%09%09forall+m%3AMale.+mother_name%28m%29+%3D+female_name%28mother%28m%29%29%0D%0A%7D%0D%0A%0D%0Ainstance+DeNormalizedData+%3D+literal+%3A+DeNormalizedSchema+%7B%0D%0A%09imports%0D%0A%09%09NormalizedData%0D%0A%7D%0D%0A";

//$encodedAqlCode = $_POST["code"];
//die($encodedAqlCode);

// this works
$encodedAqlCodeExpected = "typeside+Ty+%3D+literal+%7B%0A++java_types%0A++++String+%3D+%22java.lang.String%22%0A++java_constants%0A++++String+%3D+%22return+input%5B0%5D%22%0A%7D%0A%0Aschema+NormalizedSchema+%3D+literal+%3A+Ty+%7B%0A++entities%0A++++Male+%0A++++Female%0A++foreign_keys%0A++++mother+%3A+Male+-%3E+Female%0A++attributes%0A++++female_name+%3A+Female+-%3E+String%0A++++male_name+%3A+Male+-%3E+String+%0A%7D%0A%0Ainstance+NormalizedData+%3D+literal+%3A+NormalizedSchema+%7B%0A++generators%0A++++Al+Bob+Charlie+%3A+Male%0A++++Ellie+Fran+%3A+Female%0A++equations%0A++++Al.male_name+%3D+Albert+%0A++++Al.mother+%3D+Ellie%0A++++%0A++++Bob.male_name+%3D+George%0A++++Bob.mother+%3D+Ellie%0A++++%0A++++Charlie.male_name+%3D+Charles%0A++++Charlie.mother+%3D+Fran%0A%0A++++Ellie.female_name+%3D+Elaine%0A++++Fran.female_name+%3D+Francine%0A%7D%0A%0Aschema+DeNormalizedSchema+%3D+literal+%3A+Ty+%7B%0A++imports%0A++++NormalizedSchema%0A++attributes%0A++++mother_name+%3A+Male+-%3E+String%0A++observation_equations%0A++++forall+m%3AMale.+mother_name(m)+%3D+female_name(mother(m))%0A%7D%0A%0Ainstance+DeNormalizedData+%3D+literal+%3A+DeNormalizedSchema+%7B%0A++imports%0A++++NormalizedData%0A%7D%0A";

$encodedAqlCodeFound = urlencode($_POST["code"]);

/*if ($encodedAqlCodeExpected == $encodedAqlCodeFound) {
  //die("equal: ".$encodedAqlCodeExpected == $encodedAqlCodeFound."EQ");
  die("equal");
}
else {
  die("EXPECTED:".$encodedAqlCodeExpected."FOUND:".$encodedAqlCodeFound."END");
}*/

//if (!$encodedAqlCode) {
//  die("count: ".count($_POST)."END");
//}

$url = fopen("http://208.113.133.193/cgi-bin/try.cgi?code=".$encodedAqlCodeFound, "r");
if ($url) {
  while (!feof($url)) {
    echo fgets($url, 4096);
  }
  fclose($url);
}
?>
