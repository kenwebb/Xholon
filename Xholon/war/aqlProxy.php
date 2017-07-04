<?php
header('Content-type: text/plain');
$encodedAqlCode = urlencode($_POST["code"]);
$url = fopen("http://208.113.133.193/cgi-bin/try.cgi?code=".$encodedAqlCode, "r");
if ($url) {
  while (!feof($url)) {
    echo fgets($url, 4096);
  }
  fclose($url);
}
?>
