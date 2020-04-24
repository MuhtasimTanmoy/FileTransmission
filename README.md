# FileTransmission

Implementation of a data transmission system with the functionalities performed by data link layer.

# Features

- ## Framing
    - Implement bit-stuffing. Note that each frame is delimited by the special bit pattern '01111110' (i.e., decimal 126). The goal of bit-stuffing is to ensure that this special bit sequence does not occur inside the frame. To see what happens without bit-stuffing, use the character '~' in the payload. Implement the function of the bitDestuff for bit de-stuffing.
- ## Flow Control
    - Acknowledgement management (Feedback based flow control)
        Protocol
    - Implement protocol Go-Back-N (A1/B1)
    - Implement protocol Stop and Wait (A2/B2) -Timer based retransmission
    - Keep option for iIntroducing random lost frame (%)
- ## Error Detection and correction
    - Implementation of frame checksum calculation and checksum verification. 
    - Implement the function hasChecksumError for checksum verification at receiver
- ## Checking
  - Implementation of suitable Graphical User Interface (GUI). (Preferable)
  - Show the frame to send, then show the frame after stuffing was performed, then show it after it arrived on the receiver side (possibly with errors); then show what the receiver does with the frame – discarding or de-stuffing it (correspondingly to finding errors or not with checksum), sending acknowledgements, passing payload of frame; show timers and sequence numbers, etc.
  
The frame format might look like.
| kind of frame (Data/ack)| seqNo | ackNo | payload | checksum |
where, a. each of the fields: kind, seqNo, ackNo and checksum are of 1 byte
b. payload field is of variable size determined by the size of the 'packet' as
given by the network layer.
