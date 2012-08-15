<?php


$socket = stream_socket_server("tcp://127.0.0.1:9023", $errno, $errstr);

if (!$socket) {

  echo "$errstr ($errno)\n";

} else {

  echo "server start listening\n";

  while ( $conn = @stream_socket_accept($socket, 180))
  {
        echo "phone connected\n";
		
		$data = fread($conn, 1024);
		
		$obj = json_decode($data);
		echo "Request: ".$obj->{'message'};//{'streamName'};
		
		//$port = 8000;
		$response = "Response from server";
		fputs( $conn, $response );
		
		echo "\nPort number sent";
		
		fclose($conn);

  }
  echo "phone disconnected\n";


}
  @fclose($handle);
  fclose($socket);
?>