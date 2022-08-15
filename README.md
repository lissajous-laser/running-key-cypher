# Running Key Cypher
## Background
The [running key cypher](https://en.wikipedia.org/wiki/Running_key_cipher) is a pen and paper cypher. It is a variant of the [Vignere cypher](https://en.wikipedia.org/wiki/Vigenère_cipher), but improves on it by using a key as long as the plaintext, rather than using a repeated key. The non repeating nature of the key makes the cypertext from the running key cypher more randomised. The key could also be a section of text from a printed article, so the whole key did not need to be memorised.

The running key cypher is case insensitive and only substitutes letters, so encryption causes loss of punctuation, spaces, and distinction between uppercase and lowercase characters.

## Implementation
- The user types in text to be encoded or decoded.
- A text file is used as the key. e.g. a book from https://www.gutenberg.org/. The user is prompted to enter in how many characters to skip at the start of the file before the start of the key is reached.
- The running key cypher is not secure, and is implemented for historical interest.
- No attempt has been made to protect information the program stores in memory.