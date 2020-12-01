# Wowza Playback Authentication (SecureToken version 2)

REF: https://www.wowza.com/docs/how-to-protect-streaming-using-securetoken-in-wowza-streaming-engine

* How to make SecureHash
1. To create SecureHash, combine Parameters
2. Do encode SHA-265-HASH to *!BINARY-HEX!*
3. Do encode Base64 to *TEXT-STRING*
4. Do convert character "+, /" to "-, _"
