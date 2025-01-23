describe('Login Page', () => {
  beforeEach(() => {

    cy.intercept('POST', '**/api/auth/login', (req) => {
      const { username, motDePasse } = req.body;
      if (username === 'testuser' && motDePasse === 'validpassword') {
        req.reply({
          statusCode: 200,
          body: 'valid-token-123'
        });
      } else {
        req.reply({
          statusCode: 401,
          body: 'Unauthorized'
        });
      }
    }).as('loginRequest');

    cy.visit('/login');
  });

  it('should login successfully with valid credentials', () => {

    cy.get('#username').type('testuser');
    cy.get('#motDePasse').type('validpassword');

    cy.get('button[type="submit"]').click();

    cy.wait('@loginRequest').its('response.statusCode').should('eq', 200);

    cy.url().should('include', '/dashboard');
  });

  it('should show error with invalid credentials', () => {

    cy.get('#username').type('wronguser');
    cy.get('#motDePasse').type('wrongpassword');

    cy.get('button[type="submit"]').click();

    cy.wait('@loginRequest').its('response.statusCode').should('eq', 401);


    cy.contains('Wrong Credentials').should('be.visible');
    cy.url().should('include', '/login');
  });

});
