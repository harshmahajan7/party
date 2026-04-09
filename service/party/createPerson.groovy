def ec = context.ec

if (!partyId) { return [errorMessage: "partyId is required"] }
if (!firstName) { return [errorMessage: "firstName is required"] }
if (!lastName) { return [errorMessage: "lastName is required"] }

def party = ec.entity.find("party.Party").condition("partyId", partyId).one()

if (!party) {
    party = ec.entity.makeValue("party.Party")
    party.partyId = partyId
    party.partyTypeEnumId = "PtyPerson" 
    party.create()
}

def person = ec.entity.makeValue("party.Person")
person.setFields(context, true, null, null)
person.partyId = partyId

person.store()

return [responseMessage: "Person ${firstName} ${lastName} (ID: ${partyId}) processed successfully!"]
