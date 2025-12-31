# Style Guide

## Baseline that should go without saying
- Meet Customer Need
- Clearly Express Intent
- No Duplicate Code
- Concise As Possible

## Be attentive to abstraction boundaries
- Don't mix levels of abstraction, no combining of higher level and lower level concerns
- Single Responsibility Principle, each compilation unit should have only once concern
- Be especially diligent about how abstraction boundaries affect testability, tests should be
  - Fast, so put abstraction boundaries around slow things such as file system and network access
  - Reliable, so put abstraction boundaries around things depend on environment, such as clock or system properties
  - Comprehensible, so use abstraction to break apart complicated concepts into their component parts
