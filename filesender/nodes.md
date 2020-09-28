# filesender

### Objective
Build a program that can transfer files between two hosts. The files to be sent
can be either an entire directory or single files.

### Requirements
- Transfer files between two hosts
- Data transfer must be encrypted
- Data should be as small as possible (perhaps encode before sending)

### TODOS:

- Construct a protocol


### Technical details

#### Protocol

##### Packet
| version | command | size | <br>
| 8 | 8 | 32 | <br>
| data | <br>
| ------ |

##### [ DATA ] File-packet
| file-size | dest-size |   dest  | <br>
|     32    |  16  | ------ |

#### Encryption

#### Data transfer