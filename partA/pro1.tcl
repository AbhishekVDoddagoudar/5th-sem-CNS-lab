set ns [new Simulator]


set tracefile [open out.tr w]
$ns trace-all $tracefile


set namfile [open out.nam w]
$ns namtrace-all $namfile


set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]


$ns duplex-link $n0 $n1 10.0Mb 0.05ms DropTail
$ns queue-limit $n0 $n1 5
$ns duplex-link $n1 $n2 0.05Mb 100ms DropTail
$ns queue-limit $n1 $n2 2


set tcp0 [new Agent/TCP]
$ns attach-agent $n0 $tcp0

set sink1 [new Agent/TCPSink]
$ns attach-agent $n2 $sink1

$ns connect $tcp0 $sink1
$tcp0 set packetSize_ 100


set cbr0 [new Application/Traffic/CBR]
$cbr0 attach-agent $tcp0
$cbr0 set packetSize_ 10000
$cbr0 set rate_ 0.1Mb
$cbr0 set random_ null
$ns at 1.0 "$cbr0 start"
$ns at 10.0 "$cbr0 stop"


proc finish {} {
    global ns tracefile namfile
    $ns flush-trace
    close $tracefile
    close $namfile
    exec nam out.nam &
    exit 0
}

$ns at 10.0 "finish"

$ns run

