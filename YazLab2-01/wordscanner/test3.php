<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>WordScanner</title>
</head>
<body>
<?php
require 'vendor/autoload.php';
use voku\helper\StopWords;
$site = $_POST["url"];
$siteBol = explode("/", $site);
$site2 = $_POST["url2"];
$siteBol2 = explode("/", $site2);

if ($siteBol[0] != "http:") {
  $site = "http://".$site;
}
if ($siteBol2[0] != "http:") {
  $site2 = "http://".$site2;
}
$stopWords = new StopWords();
$contents = array();
$contents2 = array();
$contents = file_get_contents($site); 
$contents2 = file_get_contents($site2);
$contents =  str_replace(array('ı','ü','ğ','ş','ö','ç','İ','Ü','Ğ','Ş','Ö','Ç'), array('i','u','g','s','o','c','i','u','g','s','o','c'), $contents);
$contents2 =  str_replace(array('ı','ü','ğ','ş','ö','ç','İ','Ü','Ğ','Ş','Ö','Ç'), array('i','u','g','s','o','c','i','s','o','c'), $contents2);
$contents = strtolower($contents);
$contents2 = strtolower($contents2);
// Get rid of style, script etc
$search = array('@<script[^>]*?>.*?</script>@si',  // Strip out javascript
           '@<head>.*?</head>@siU',            // Lose the head section
           '@<style[^>]*?>.*?</style>@siU',    // Strip style tags properly
           '@<![\s\S]*?--[ \t\n\r]*>@'        // Strip multi-line comments including CDATA
);


$contents = preg_replace($search, '', $contents); 
$contents2 = preg_replace($search, '', $contents2);
$stopWords->getStopWordsFromLanguage('tr');
//$contents = array_diff($contents, $stopWords);


$result = array_count_values(
              str_word_count(
                  strip_tags($contents), 1
                  )
              );
$result2 = array_count_values(
              str_word_count(
                  strip_tags($contents2), 1
                  )
              );
arsort($result);
$result = array_splice($result, 0, 5);
echo '<pre>';
print_r($result);
echo '</pre>';
arsort($result2);
$result2 = array_splice($result2, 0, 5);
echo '<pre>';
print_r($result2);
echo '</pre>';

$toplambenzer = array_sum((array_intersect_key($result2, $result)));
$toplamkelime = array_sum($result2);
$skor = ($toplambenzer/$toplamkelime)*100;
echo $site2, "'nin ", $site, "'ye benzerlik skoru : ", $skor;
  





?>
<br>
<a href="asamaform.php">Aşama 1</a> - <a href="asamaform2.php">Aşama 2</a> - <a href="asamaform3.php">Aşama 3</a> - <a href="asamaform5.php">Aşama 5</a>
</body>