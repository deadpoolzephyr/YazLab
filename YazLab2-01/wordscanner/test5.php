<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>WordScanner</title>
</head>
<body>
<?php
$site = $_POST["url"];
$siteBol = explode("/", $site);

if ($siteBol[0] != "http:") {
	$site = "http://".$site;
}
$contents = file_get_contents($site);
$contents =  str_replace(array('ı','ü','ğ','ş','ö','ç','İ','Ü','Ğ','Ş','Ö','Ç'), array('i','u','g','s','o','c','i','u','g','s','o','c'), $contents);
$contents = strtolower($contents);
// Get rid of style, script etc
$search = array('@<script[^>]*?>.*?</script>@si',  // Strip out javascript
           '@<head>.*?</head>@siU',            // Lose the head section
           '@<style[^>]*?>.*?</style>@siU',    // Strip style tags properly
           '@<![\s\S]*?--[ \t\n\r]*>@'        // Strip multi-line comments including CDATA
);


$contents = preg_replace($search, '', $contents); 

$result = array_count_values(
              str_word_count(
                  strip_tags($contents), 1
                  )
              );

$oku = fopen("esanlamlilar.txt", "r");
while (!feof($oku)) {
	$satir = fgets($oku);
	echo $satir, "<br>";
}
fclose($oku);
$synon1 = strstr('-', $satir);
$synon2 = strstr('-', $satir, true);
echo $synon1, "<br>";
echo $synon2, "<br>";

arsort($result);
$result2 = array_flip($result);
echo '<pre>';
print_r($result);
echo '</pre>';
?>
<br>
<a href="asamaform.php">Aşama 1</a> - <a href="asamaform2.php">Aşama 2</a> - <a href="asamaform3.php">Aşama 3</a> - <a href="asamaform5.php">Aşama 5</a>
</body>