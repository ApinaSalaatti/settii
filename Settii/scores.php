<?php

	chooseAction();
	
	function chooseAction() {
		$action = $_POST['action'];
		
		if($action == 'test') {
			test($action);
		}
		else if($action == 'get') {
			getScores();
		}
		else if($action == 'send') {
			addScore();
		}

	}
	
	function test($t) {
		echo $t;
	}
	
	function getScores() {
		$fileString = file_get_contents('scores.txt');
		$fileStrings = explode(";", $fileString);
		
		echo getTopTen($fileStrings);
	}
	
	function getTopTen($fileStrings) {
		$indx = 0;
		$size = sizeof($fileStrings);
		$topTen = "";
		while($indx < 10) {
			if($indx < $size) {
				$topTen = $topTen . $fileStrings[$indx] . ";";
			}
			$indx++;
		}
		
		return $topTen;
	}
	
	function addScore() {
		$fileString = file_get_contents('scores.txt');
		$fp = fopen('scores.txt', 'w');
		
		$name = $_POST['name'];
		$score = $_POST['score'];
		$written = false;

		if($name != '' && $name != null && $score != '' && $score != null) {
			$fileStrings = explode(";", $fileString);
			foreach($fileStrings as $fString) {
				$fStrings = explode(":", $fString);
				if(strlen($fStrings[0]) > 0 && strlen($fStrings[1]) > 0 && $fStrings[1] > $score) {
					fwrite($fp, $fStrings[0] . ':' . $fStrings[1] . ';');
				}
				else {
					if($written == false) {
						fwrite($fp, $name . ':' . $score . ';');
						$written = true;
					}
					if(strlen($fStrings[0]) > 0 && strlen($fStrings[1]) > 0) {
						fwrite($fp, $fStrings[0] . ':' . $fStrings[1] . ';');
					}
				}
			}
		}
		
		fclose($fp);
	}
	
?>