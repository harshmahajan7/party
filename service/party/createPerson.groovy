// Access Execution Context
def ec = context.ec

// ================= VALIDATION =================
if (!partyId) { return [errorMessage: "partyId is required"] }
if (!firstName) { return [errorMessage: "firstName is required"] }
if (!lastName) { return [errorMessage: "lastName is required"] }

// ================= AUTO-CREATE PARTY =================
// Check if the base Party record exists
def party = ec.entity.find("party.Party").condition("partyId", partyId).one()

if (!party) {
    // Automatically create the Party record if it doesn't exist
    party = ec.entity.makeValue("party.Party")
    party.partyId = partyId
    party.partyTypeEnumId = "PtyPerson" // Default to Person type
    party.create()
}

// ================= STORE PERSON =================
// Using store() instead of create() handles both new records and updates (Idempotent)
def person = ec.entity.makeValue("party.Person")
person.setFields(context, true, null, null)
person.partyId = partyId

// This will perform a createOrUpdate based on the primary key
person.store()

// ================= RESPONSE =================
return [responseMessage: "Person ${firstName} ${lastName} (ID: ${partyId}) processed successfully!"]