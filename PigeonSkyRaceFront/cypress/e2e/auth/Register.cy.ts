describe('Register Page', () => {
  beforeEach(() => {
    cy.intercept('POST', '**/api/auth/register', (req) => {
      const { username, motDePasse, nom, latitude, longitude } = req.body;

      if (!username || !motDePasse || !nom || latitude === undefined || longitude === undefined) {
        req.reply({ statusCode: 400, body: 'Invalid registration data' });
      } else if (username === 'existinguser') {
        req.reply({ statusCode: 409, body: 'Username already exists' });
      } else {
        req.reply({ statusCode: 201, body: 'Registration successful' });
      }
    }).as('registerRequest');

    cy.visit('/register');
  });

  it('should register successfully with valid data', () => {
    const uniqueUsername = `user${Date.now()}`;
    cy.get('#username').type(uniqueUsername);
    cy.get('#motDePasse').type('Password123!');
    cy.get('#nom').type('Fred Test');
    cy.get('#latitude').clear().type('40.7128');
    cy.get('#longitude').clear().type('-74.0060');
    cy.get('button[type="submit"]').click();

    cy.wait('@registerRequest').its('response.statusCode').should('eq', 201);
    cy.url().should('include', '/');
  });

  it('should show error for existing username', () => {
    cy.get('#username').type('existinguser');
    cy.get('#motDePasse').type('Password123!');
    cy.get('#nom').type('Existing User');
    cy.get('#latitude').clear().type('40.7128');
    cy.get('#longitude').clear().type('-74.0060');
    cy.get('button[type="submit"]').click();

    cy.wait('@registerRequest').its('response.statusCode').should('eq', 409);
    cy.contains('Username already exists').should('be.visible');
  });

  it('should validate password strength', () => {
    cy.get('#username').type('newuser');
    cy.get('#motDePasse').type('weak');
    cy.get('#nom').type('Weak Password');
    cy.get('#latitude').clear().type('40.7128');
    cy.get('#longitude').clear().type('-74.0060');
    cy.get('button[type="submit"]').click();

    cy.get('#motDePasse').should('have.class', 'is-invalid');
    cy.contains('Password must be at least 6 characters long').should('be.visible');
  });


  it('should validate latitude and longitude ranges', () => {
    cy.get('#username').type('locationuser');
    cy.get('#motDePasse').type('ValidPassword123!');
    cy.get('#nom').type('Location User');
    cy.get('#latitude').clear().type('200');
    cy.get('#longitude').clear().type('300');
    cy.get('button[type="submit"]').click();

    cy.get('#latitude').should('have.class', 'is-invalid');
    cy.get('#longitude').should('have.class', 'is-invalid');
    cy.contains('Invalid latitude (must be between -90 and 90)').should('be.visible');
    cy.contains('Invalid longitude (must be between -180 and 180)').should('be.visible');
  });

  it('should navigate to login page', () => {
    cy.contains('Already have an account?').click();
    cy.url().should('include', '/login');
  });
});
