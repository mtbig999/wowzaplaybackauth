<?php
	$server = 'http://123.123.123.123:1935';
	$application = 'testlive';
	$instance = '_definst_';
	$stream = 'testlive.stream';
	$streamName = $application.'/'. $instance.'/'.$stream;
	$secretKey = 'qw32c8w1a6s51asd3';
	$prefixParam = 't_';
	$endtimeParam = 'endtime=';
	$endtime = '1603776531';
	$starttimeParam = 'starttime=';
	$starttime = '0';
	$streamParam = $secretKey.'&'.$prefixParam.$endtimeParam.$endtime.'&'.$prefixParam.$starttimeParam.$starttime;
	#echo nl2br('beforeHashStr: '.$streamName.'?'.$streamParam.'\n');
	$hashStr = hash('sha256', $streamName.'?'.$streamParam, true); // MUST Hash be RAW
	$usableHash= strtr(base64_encode($hashStr), '+/', '-_');
	#echo nl2br('secretHash: '.$usableHash);
	$hlsMetafile = 'playlist.m3u8';
	$hashParam = 'hash=';
	echo nl2br('secretURL: '.$server.'/'.$streamName.'/'.$hlsMetafile.'?'.$prefixParam.$endtimeParam.$endtime.'&'.$prefixParam.$starttimeParam.$starttime.'&'.$prefixParam.$hashParam.$usableHash);
?>
