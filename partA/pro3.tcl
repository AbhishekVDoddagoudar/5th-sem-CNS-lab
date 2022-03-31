set ns [new Simulator]
set tracefile [open out.tr w]
$ns trace-all $tracefile
set namfile [open out.nam w]
$ns namtrace-all $namfile 
set winFile0 [open WinFile0 w]
set winFile1 [open WinFile1 w]


set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]
set n3 [$ns node]
set n4 [$ns node]
set n5 [$ns node]

$ns duplex-link $n0 $n2 10.0Mb 1ms DropTail
$ns queue-limit $n0 $n2 10

$ns duplex-link $n1 $n2 10.0Mb 1ms DropTail
$ns queue-limit $n1 $n2 10

$ns duplex-link $n2 $n3 10.0Mb 1ms DropTail
$ns queue-limit $n2 $n3 10

$ns duplex-link $n3 $n2 10.0Mb 1ms DropTail
$ns queue-limit $n3 $n2 10

$ns duplex-link-op $n0 $n2 orient right-down
$ns duplex-link-op $n1 $n2 orient right-up
$ns simplex-link-op $n2 $n3 orient right
$ns simplex-link-op $n3 $n2 orient left

set lan [$ns newLan "$n3 $n4 $n5" 0.5Mb 40ms LL Queue/DropTail MAC/802_3 Channel]

set tcp0 [new Agent/TCP/Newreno]
$ns attach-agent $n0 $tcp0

set sink2 [new Agent/TCPSink]
$ns attach-agent $n4 $sink2
$ns connect $tcp0 $sink2
$tcp0 set packetSize_ 1500
$tcp0 set window 5000

set tcp1 [new Agent/TCP/Newreno]
$ns attach-agent $n5 $tcp1

set sink3 [new Agent/TCPSink]
$ns attach-agent $n1 $sink3
$ns connect $tcp1 $sink3
$tcp1 set packetSize_ 1500
$tcp1 set window 500

set ftp0 [new Application/FTP]
$ftp0 attach-agent $tcp0
$ns at 1.0 "$ftp0 start"
$ns at 10.0 "$ftp0 stop"

set ftp1 [new Application/FTP]
$ftp1 attach-agent $tcp1
$ns at 1.0 "$ftp1 start"
$ns at 10.0 "$ftp1 stop"

set var [new ErrorModel]
$var ranvar [new RandomVariable/Uniform]
$var drop-target [new Agent/Null]
$ns lossmodel $var $n2 $n3

proc finish {} {
	global ns tracefile namfile
	$ns flush-trace
	close $tracefile
	close $namfile
	exec nam out.nam &
	exec xgraph WinFile0 WinFile1 &   
	exit 0
}
proc PlotWindow {tcpSource file} { 
	global ns 
	set time 0.1	
	set now [$ns now]  
	set cwnd [$tcpSource set cwnd_] 
	puts $file "$now $cwnd"   
	$ns at [expr $now+$time] "PlotWindow $tcpSource $file" 
}  
 


$ns at 0.1 "PlotWindow $tcp0 $winFile0"
$ns at 0.1 "PlotWindow $tcp1 $winFile1"
$ns at 10.0 "finish"

$ns run

