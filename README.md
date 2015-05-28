# azuqua.android
The android client for Azuqua

<h3> Step by step guide to implement the azuqua.android client. <h3>
<h4> 1. Instatiate azuqua.android client</h4>
Azuqua azuqua = new Azuqua( ); <br>
<h4> 2. Get Orgs</h4>

                azuqua.login(username, password, new AzuquaOrgRequest() {
                    @Override
                    public void onResponse(Collection<Org> orgsCollection) {
                      //returns collection of orgs on successfull request
                    }

                    @Override
                    public void onErrorResponse(String error) {
                      //returns error on bad request
                    }
                });
<h4> 3. Get All Flos for an Org</h4>

                azuqua.getFlos(new AzuquaAllFlosRequest() {
                    @Override
                    public void onResponse(Collection<Flo> flosCollection) {
                      //returns collection of flos on successfull request
                    }
        
                    @Override
                    public void onErrorResponse(String error) {
                      //returns error error on bad request
                    }
                });
<h4> 4. Read a Flo </h4>
                flo.read(new AzuquaFloRequest() {
                    @Override
                    public void onResponse(Flo floData) {
                      //returns flo object on successfull request
                    }
                    @Override
                    public void onErrorResponse(String error) {
                      //returns error error on bad request  
                    }
                });
                
<h4> 5. Enable a Flo </h4>
                flo.enable(new AzuquaFloRequest() {
                    @Override
                    public void onResponse(Flo floData) {
                      //returns flo object on successfull request  
                    }
                    @Override
                    public void onErrorResponse(String error) {
                      //returns error error on bad request  
                    }
                });
<h4> 6. Disable a Flo </h4>
                flo.disable(new AzuquaFloRequest() {
                    @Override
                    public void onResponse(Flo floData) {
                      //returns flo object on successfull request  
                    }
                    @Override
                    public void onErrorResponse(String error) {
                      //returns error error on bad request  
                    }
                });
                
<h4> 7. Invoke a Flo </h4>
              flo.invoke(object.toString(), new AsyncResponse() {
                    @Override
                    public void onResponse(String response) {
                      //returns a json object as a string on successfull request
                    }
                    @Override
                    public void onErrorResponse(String error) {
                      //returns error error on bad request
                    }
